package pw.coding.konnecto.domain.models

import pw.coding.konnecto.domain.util.ActivityAction

data class Activity(
    val username : String,
    val actionType: ActivityAction,
    val formattedTime : String
)
