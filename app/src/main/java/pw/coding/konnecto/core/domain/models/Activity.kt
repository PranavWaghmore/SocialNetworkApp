package pw.coding.konnecto.core.domain.models

import pw.coding.konnecto.feature_activity.domain.ActivityType

data class Activity(
    val userId: String,
    val parentId: String,
    val username : String,
    val activityType: ActivityType,
    val formattedTime : String
)
