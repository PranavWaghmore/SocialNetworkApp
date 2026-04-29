package pw.coding.konnecto.feature_post.presentation.personlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_post.domain.use_case.PostUseCases
import pw.coding.konnecto.feature_profile.domain.use_case.ProfileUseCases
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(PersonListState())
    val state: State<PersonListState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("parentId")?.let { parentId ->
            loadUsersWhoLikedParent(parentId)
        }
    }

    fun onEvent(event: PersonListEvent){

        when(event){

            is PersonListEvent.ToggleFollow -> {
                toggleFollowState(event.userId)
            }

            is PersonListEvent.GetUserProfile -> {

            }
        }
    }

    private fun loadUsersWhoLikedParent(parentId: String){
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            val result = postUseCases.getUsersWhoLikedParent(parentId)
            when(result){
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        users = result.data ?: emptyList()
                    )
                    _state.value = state.value.copy(isLoading = false)
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                    _state.value = state.value.copy(isLoading = false)
                }
            }
        }
    }


    private fun toggleFollowState(userId: String) {
        viewModelScope.launch {

            val currentState = state.value
            val user = currentState.users.find { it.userId == userId } ?: return@launch
            val oldFollowState = user.isFollowing
            val newFollowState = !user.isFollowing

            _state.value = currentState.copy(
                users = currentState.users.map {
                    if (it.userId == userId) it.copy(isFollowing = newFollowState)
                    else it
                }
            )

            val result = profileUseCases.toggleFollowStateForUser(
                userId = userId,
                isFollowing = oldFollowState
            )

            when(result){
                is Resource.Success -> Unit
                is Resource.Error -> {

                    _state.value = _state.value.copy(
                        users = _state.value.users.map {
                            if(it.userId == userId) it.copy(isFollowing = oldFollowState)
                            else it
                        }
                    )

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }


}