package pw.coding.konnecto.feature_post.presentation.post_detail

sealed class PostDetailEvent {
    data class EnteredComment(val comment: String): PostDetailEvent()
    object LikePost: PostDetailEvent()
    object Comment: PostDetailEvent()
    data class LikeComment(val commentId: String): PostDetailEvent()
    object SharePost: PostDetailEvent()
}