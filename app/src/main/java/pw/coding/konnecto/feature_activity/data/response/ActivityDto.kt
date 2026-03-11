package pw.coding.konnecto.feature_activity.data.response

import pw.coding.konnecto.core.domain.models.Activity
import pw.coding.konnecto.core.presentation.util.DateFormatUtil
import pw.coding.konnecto.feature_activity.domain.ActivityType
import java.text.SimpleDateFormat
import java.util.Locale

data class ActivityDto(
    val timeStamp: Long,
    val userId: String,
    val parentId: String,
    val type: Int,
    val username : String,
    val id : String,
){
    fun toActivity(): Activity{
        return Activity(
            userId = userId,
           parentId = parentId,
            username = username,
            activityType = when(type) {
                ActivityType.FollowedUser.type -> ActivityType.FollowedUser
                ActivityType.LikedPost.type -> ActivityType.LikedPost
                ActivityType.LikedComment.type -> ActivityType.LikedComment
                ActivityType.CommentedOnPost.type -> ActivityType.CommentedOnPost
                else -> ActivityType.FollowedUser
            },
            formattedTime = DateFormatUtil.timestampToFormattedString(
                timeStamp,
                pattern = "MMM dd, HH:mm"
            )
        )
    }
}
