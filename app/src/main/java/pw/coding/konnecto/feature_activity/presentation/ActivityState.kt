package pw.coding.konnecto.feature_activity.presentation

import pw.coding.konnecto.core.domain.models.Activity

data class ActivityState(
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false
)
