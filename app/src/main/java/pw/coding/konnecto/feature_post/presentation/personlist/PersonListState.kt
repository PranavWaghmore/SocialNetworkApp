package pw.coding.konnecto.feature_post.presentation.personlist

import pw.coding.konnecto.core.domain.models.UserItem

data class PersonListState(
    val users: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
