package pw.coding.konnecto.feature_chat.presentation.message

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.domain.states.StandardTextFieldState
import pw.coding.konnecto.core.presentation.PagingState
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.util.DefaultPaginator
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_chat.domain.models.Message
import pw.coding.konnecto.feature_chat.domain.use_case.ChatUseCases
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases, private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _messageTextField = mutableStateOf(StandardTextFieldState())
    val messageTextField: State<StandardTextFieldState> = _messageTextField

    private val _pagingState = mutableStateOf<PagingState<Message>>(PagingState())
    val pagingState: State<PagingState<Message>> = _pagingState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(onLoadUpdated = { isLoading ->
        _pagingState.value = _pagingState.value.copy(
            isLoading = isLoading
        )
    }, onRequest = { nextPage ->
        savedStateHandle.get<String>("chatId")?.let { chatId ->
            chatUseCases.getMessagesForChat(chatId, nextPage)
        } ?: Resource.Error(UiText.unknownError())
    }, onError = { uiText ->
        _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
    }, onSuccess = { messages ->
        _pagingState.value = pagingState.value.copy(
            items = pagingState.value.items + messages,
            isLoading = false,
            endReached = messages.isEmpty()
        )
    })

    init {
        loadNextMessages()
        observerChatEvents()
    }

    fun loadNextMessages() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun observeChatMessages(){
        chatUseCases.observeMessages()
            .onEach { message ->
                _pagingState.value = pagingState.value.copy(
                    items = _pagingState.value.items + message
                )
            }.launchIn(viewModelScope)
    }
    private fun observerChatEvents() {
        chatUseCases.observeChatEvents().onEach { event ->
            when(event) {

                is WebSocket.Event.OnConnectionOpened<*> -> {
                    observeChatMessages()
                }
                is WebSocket.Event.OnConnectionFailed -> {
                    println("Connection Failed : ${event.throwable}")
                }

                else -> {}
            }
            }.launchIn(viewModelScope)
    }

    private fun sendMessages() {
        val toId = savedStateHandle.get<String>("remoteUserId") ?: return

        if (messageTextField.value.text.isBlank()) {
            return
        }

        val chatId = savedStateHandle.get<String>("chatId")
        chatUseCases.sendMessage(toId, messageTextField.value.text.trim(), chatId)

    }

    fun onEvent(event: MessageEvent) {

        when (event) {

            is MessageEvent.EnteredMessage -> {
                _messageTextField.value = messageTextField.value.copy(
                    text = event.message
                )
            }

            is MessageEvent.SendMessage -> {
                sendMessages()
            }
        }
    }
}