package pw.coding.konnecto.feature_post.presentation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.feature_post.data.paging.PostSource
import pw.coding.konnecto.feature_post.domain.use_case.PostUseCases
import javax.inject.Inject

@HiltViewModel()
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases
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