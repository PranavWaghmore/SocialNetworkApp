package pw.coding.konnecto.feature_post.presentation.main_feed

sealed class MainFeedEvent {
    data class OnLiked(val postId: String): MainFeedEvent()
}