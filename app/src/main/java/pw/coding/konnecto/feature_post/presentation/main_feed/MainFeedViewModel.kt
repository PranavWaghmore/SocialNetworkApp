package pw.coding.konnecto.feature_post.presentation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import pw.coding.konnecto.feature_post.domain.use_case.PostUseCases
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
      postUseCases: PostUseCases
): ViewModel(){

    private val _state = mutableStateOf(MainFeedState())
    val state: State<MainFeedState> = _state

    val posts = postUseCases.getPostForFollowsUseCase()
        .cachedIn(viewModelScope)

    fun onEvent(event : MainFeedEvent){
        when(event){
            is MainFeedEvent.LoadMorePosts ->{
                _state.value = _state.value.copy(
                    isLoadingNewPost = true
                )
            }
            is MainFeedEvent.LoadedPage -> {
                _state.value=_state.value.copy(
                    isLoadingFirstTime = false,
                    isLoadingNewPost = false
                )
            }
        }
    }
}