package pw.coding.konnecto.feature_post.presentation.personlist

sealed class PersonListEvent {
    data class ToggleFollow(val userId: String): PersonListEvent()
    data class GetUserProfile(val userId: String): PersonListEvent()
}