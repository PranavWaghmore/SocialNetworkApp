package eu.tutorials.socialnetwork.domain.models

import eu.tutorials.socialnetwork.domain.util.ActivityAction

data class Activity(
    val username : String,
    val actionType: ActivityAction,
    val formattedTime : String
)
