package pw.coding.konnecto.feature_profile.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.domain.use_case.GetOwnUserIdUseCase
import pw.coding.konnecto.core.presentation.PagingState
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.util.DefaultPaginator
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_post.domain.use_case.PostUseCases
import pw.coding.konnecto.feature_post.util.ParentType
import pw.coding.konnecto.feature_profile.domain.use_case.ProfileUseCases
import pw.coding.konnecto.feature_profile.presentation.profile.components.ProfileToolBarState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val postUseCases: PostUseCases,
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _toolbarState = mutableStateOf(ProfileToolBarState())
    val toolbarState: State<ProfileToolBarState> = _toolbarState

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState


    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page->

            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserId()
            profileUseCases.getPostsForProfile(
                userId = userId,
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

    fun setExpandedRatio(ratio: Float) {
        _toolbarState.value = _toolbarState.value.copy(expandedRatio = ratio)
    }

    fun setToolbarOffset(value: Float) {
        _toolbarState.value = _toolbarState.value.copy(toolbarOffsetY = value)
    }

    init {
        loadNextPosts()
    }

    fun onEvent(event: ProfileEvent){

        when(event){

            is ProfileEvent.LikePost -> {
                toggleLikeState(parentId = event.postId)
            }

            is ProfileEvent.GetProfile -> {

            }
        }
    }

    fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            val result = profileUseCases.getProfile(
                userId ?: getOwnUserId()
            )
            when (result){
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false, profile = result.data
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(isLoading = false)
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    fun loadNextPosts(){
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