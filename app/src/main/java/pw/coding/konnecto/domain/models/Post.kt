package pw.coding.konnecto.domain.models

data class Post(
    val username: String,
    val imageUrl: String,
    val postPictureUrl: String,
    val description: String,
    val likeCount: Int,
    val commentCount: Int
)
