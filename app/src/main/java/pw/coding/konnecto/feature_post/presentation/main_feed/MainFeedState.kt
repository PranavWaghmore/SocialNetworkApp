package pw.coding.konnecto.feature_post.presentation.main_feed

data class MainFeedState(
    val isLoadingFirstTime: Boolean = true,
    val isLoadingNewPost: Boolean = false,
)
