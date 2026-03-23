package pw.coding.konnecto.core.domain.models

data class Comment(
    val id : String,
    val username: String,
    val profilePictureUrl : String,
    val timestamp: Long,
    val comment : String,
    val isLiked : Boolean,
    val likeCount : Int
)
