package pw.coding.konnecto.feature_profile.presentation.search

import pw.coding.konnecto.core.domain.models.UserItem

data class SearchState(
    val userItems: List<UserItem> = emptyList(),
    val isLoading : Boolean = false
)
