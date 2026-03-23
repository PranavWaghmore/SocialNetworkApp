package pw.coding.konnecto.feature_post.data.remote.dto

import pw.coding.konnecto.core.domain.models.Comment

data class CommentDto(
    val id : String,
    val username: String,
    val profilePictureUrl : String,
    val timestamp: Long,
    val comment : String,
    val isLiked : Boolean,
    val likeCount : Int
){
    fun toComment(): Comment{
        return Comment(
            id = id,
            username = username,
            profilePictureUrl = profilePictureUrl,
            timestamp = timestamp,
            comment = comment,
            isLiked = isLiked,
            likeCount = likeCount
        )
    }
}
