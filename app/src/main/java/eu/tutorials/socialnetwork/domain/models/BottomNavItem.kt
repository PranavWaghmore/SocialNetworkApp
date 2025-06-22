package eu.tutorials.socialnetwork.domain.models

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.annotation.StringRes

data class BottomNavItem(
    val route : String,
    val icon : ImageVector?=null,
    val contentDescription: String?=null,
    val alertCount :Int?=null,
)
