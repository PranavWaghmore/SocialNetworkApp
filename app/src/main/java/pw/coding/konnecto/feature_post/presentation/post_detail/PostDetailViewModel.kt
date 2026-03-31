package pw.coding.konnecto.feature_post.presentation.post_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.states.StandardTextFieldState
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_auth.domain.use_case.AuthenticateUseCase
import pw.coding.konnecto.feature_post.domain.use_case.PostUseCases
import pw.coding.konnecto.feature_post.util.ParentType
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val authenticate: AuthenticateUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _commentTextFieldState = mutableStateOf(StandardTextFieldState())
    val commentTextFieldState: State<StandardTextFieldState> = _commentTextFieldState

    private val _commentState = mutableStateOf(CommentState())
    val commentState: State<CommentState> = _commentState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            loadPost(postId)
            loadComments(postId)
        }
    }

    var isUserLoggedIn = false

    fun onEvent(event: PostDetailEvent) {

        when (event) {
            is PostDetailEvent.LikePost -> {
                val currentPost = _state.value.post ?: return
                _state.value = state.value.copy(
                    post = currentPost.copy(
                        isLiked = !currentPost.isLiked,
                        likeCount = if (currentPost.isLiked) {
                            currentPost.likeCount - 1
                        } else {
                            currentPost.likeCount + 1
                        }
                    )
                )

                val isLiked = currentPost.isLiked
                toggleLikeState(
                    isLiked = isLiked,
                    parentId = event.postId,
                    parentType = ParentType.Post.type
                )
            }

            is PostDetailEvent.EnteredComment -> {
                _commentTextFieldState.value = commentTextFieldState.value.copy(
                    text = event.comment
                )
            }

            is PostDetailEvent.Comment -> {
                addComment(
                    comment = commentTextFieldState.value.text,
                    postId = savedStateHandle.get<String>("postId") ?: "",
                )
            }

            is PostDetailEvent.LikeComment -> {
                var isLiked: Boolean? = null
                val updatedComments = _state.value.comments.map { comment ->

                    if (comment.id == event.commentId) {

                        val currentLiked = comment.isLiked
                        isLiked = currentLiked
                        comment.copy(
                            isLiked = !currentLiked,
                            likeCount = if (currentLiked) {
                                comment.likeCount - 1
                            } else {
                                comment.likeCount + 1
                            }
                        )
                    } else {
                        comment
                    }
                }

                if (isLiked == null) {
                    return
                }
                _state.value = _state.value.copy(
                    comments = updatedComments
                )

                toggleLikeState(
                    parentId = event.commentId,
                    parentType = ParentType.Comment.type,
                    isLiked = isLiked
                )
            }
        }
    }

    private fun loadPost(postId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingPost = true
            )
            val result = postUseCases.getPostDetails(postId)
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        post = result.data,
                        isLoadingPost = false
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingPost = false
                    )

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    private fun loadComments(postId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingComments = true
            )

            val result = postUseCases.getComments(postId)
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        comments = result.data ?: emptyList(),
                        isLoadingComments = false
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingComments = false
                    )
                }
            }
        }
    }


    private fun addComment(
        comment: String,
        postId: String
    ) {
        viewModelScope.launch {
            isUserLoggedIn = authenticate() is Resource.Success
            if(!isUserLoggedIn){
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        uiText = UiText.StringResource(R.string.please_login_first)
                    )
                )
                return@launch
            }
            _commentState.value = commentState.value.copy(
                isLoading = true
            )
            val result = postUseCases.addComment(
                comment,
                postId
            )

            when (result) {
                is Resource.Success -> {
                    _commentState.value = commentState.value.copy(
                        isLoading = false
                    )
                    _commentTextFieldState.value = commentTextFieldState.value.copy(
                        text = ""
                    )
                    loadComments(postId)
                }

                is Resource.Error -> {
                    _commentState.value = commentState.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    private fun toggleLikeState(
        isLiked: Boolean,
        parentId: String,
        parentType: Int
    ) {
        viewModelScope.launch {
            isUserLoggedIn = authenticate() is Resource.Success
            if(!isUserLoggedIn){
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        uiText = UiText.StringResource(R.string.please_login_first)
                    )
                )
                return@launch
            }
            val result = postUseCases.toggleLikeForParent(isLiked, parentId, parentType)
            when (result) {
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