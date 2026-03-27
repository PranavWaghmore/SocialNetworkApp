package pw.coding.konnecto.feature_profile.presentation.profile

sealed class ProfileEvent {
    data class GetProfile(val userId: String): ProfileEvent()
    data class LikePost(val postId: String): ProfileEvent()
    data class Follow(val userId: String): ProfileEvent()
}