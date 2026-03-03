package pw.coding.konnecto.feature_profile.data.response

import pw.coding.konnecto.feature_profile.domain.model.UserItem

data class UserResponseItem(
    val userId: String,
    val username: String,
    val profilePictureUrl: String,
    val bio: String,
    val isFollowing: Boolean
){

    fun toUserItem(): UserItem{
        return UserItem(
            userId = userId,
            username = username,
            profilePictureUrl = profilePictureUrl,
            bio = bio,
            isFollowing = isFollowing
        )
    }
}
