package pw.coding.konnecto.feature_post.presentation.main_feed

sealed class MainFeedEvent {
    data class OnPostLiked(val postId: String): MainFeedEvent()
}