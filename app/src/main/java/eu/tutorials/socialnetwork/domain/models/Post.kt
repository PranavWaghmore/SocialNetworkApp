package eu.tutorials.socialnetwork.domain.models

import androidx.compose.ui.graphics.painter.Painter

data class Post(
    val image: Painter,
    val username: String,
    val imageUrl: String,
    val postPictureUrl: String,
    val description: String,
    val likeCount: Int,
    val commentCount: Int
)
