package pw.coding.konnecto.core.domain.util

 sealed class ActivityAction {
     object LikedPost : ActivityAction()
     object CommentedOnPost : ActivityAction()
     object FollowedYou : ActivityAction()
}