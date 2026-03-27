package pw.coding.konnecto.feature_post.presentation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.presentation.PagingState
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.util.DefaultPaginator
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_post.domain.use_case.PostUseCases
import pw.coding.konnecto.feature_post.util.ParentType
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
      private val postUseCases: PostUseCases
): ViewModel(){

    private val _state = mutableStateOf(MainFeedState())
    val state: State<MainFeedState> = _state

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(

        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            postUseCases.getPostForFollows(
                page = page,
            )
        },
        onSuccess = { posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                isLoading = false,
                endReached = posts.isEmpty()
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
        }
    )

    init {
        loadNextItems()
    }
    fun onEvent(event : MainFeedEvent){
        when(event){
            is MainFeedEvent.OnPostLiked ->{
                toggleLikeState(event.postId)
            }
        }
    }

    fun loadNextItems(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }


    private fun toggleLikeState(
        parentId: String
    ){
        viewModelScope.launch {

            var isLiked: Boolean ?= null
            val updatedItems = _pagingState.value.items.map { post ->
                if(post.id == parentId){
                    val currentLiked  = post.isLiked
                    isLiked = currentLiked

                    post.copy(
                        isLiked = !currentLiked,
                        likeCount = if(currentLiked){
                            post.likeCount - 1
                        }else post.likeCount + 1
                    )
                }else{
                    post
                }
            }
            if(isLiked == null) return@launch

            _pagingState.value = _pagingState.value.copy(
                items = updatedItems
            )
            val result = postUseCases.toggleLikeForParent(
                isLiked = isLiked,
                parentId = parentId,
                parentType = ParentType.Post.type
            )
            when(result){
                is Resource.Success -> Unit
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }
}