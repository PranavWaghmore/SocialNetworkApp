package pw.coding.konnecto.core.domain.models

import pw.coding.konnecto.core.domain.util.ActivityAction

data class Activity(
    val username : String,
    val actionType: ActivityAction,
    val formattedTime : String
)
