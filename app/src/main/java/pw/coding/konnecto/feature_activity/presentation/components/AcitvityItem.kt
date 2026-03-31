package pw.coding.konnecto.feature_activity.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.Activity
import pw.coding.konnecto.core.presentation.ui.theme.SmallSpace
import pw.coding.konnecto.core.presentation.ui.theme.TextWhite
import pw.coding.konnecto.core.util.Screen
import pw.coding.konnecto.feature_activity.domain.ActivityType

@Composable
fun ActivityItem(
    modifier: Modifier,
    onNavigate: (String) -> Unit = {},
    activity: Activity
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SmallSpace),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val fillerText = when (activity.activityType) {
                is ActivityType.LikedPost ->
                    stringResource(id = R.string.liked)

                is ActivityType.CommentedOnPost ->
                    stringResource(id = R.string.commented_on)

                is ActivityType.LikedComment -> {
                    stringResource(R.string.liked)
                }

                is ActivityType.FollowedUser ->
                    stringResource(id = R.string.followed_you)
            }
            val actionText = when (activity.activityType) {
                is ActivityType.LikedPost ->
                    stringResource(R.string.you_post)

                is ActivityType.CommentedOnPost ->
                    stringResource(R.string.you_post)

                is ActivityType.LikedComment ->
                    stringResource(R.string.your_comment)

                is ActivityType.FollowedUser -> ""
            }

            val linkStyle = TextLinkStyles(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            )
            val activityText = buildAnnotatedString {
                withLink(
                    LinkAnnotation.Clickable(
                        tag = "username",
                        styles = linkStyle,
                        linkInteractionListener = {
                            onNavigate(Screen.ProfileScreen.route + "?userId=${activity.userId}")
                        }
                    )
                ) {
                    append(activity.username)
                }
                append(" $fillerText")
                if (actionText.isNotBlank()) {
                    append(" ")
                    withLink(
                        LinkAnnotation.Clickable(
                            tag = "action",
                            styles = linkStyle,
                            linkInteractionListener = {
                                val parentId = activity.parentId
                                if (parentId.isNotBlank() &&
                                    activity.activityType !is ActivityType.LikedComment) {
                                    onNavigate(Screen.PostDetailScreen.route + "/${activity.parentId}")
                                }
                            }
                        )
                    ) {
                        append(actionText)
                    }
                }
            }
            Text(
                text = activityText,
                fontSize = 12.sp,
                color = TextWhite,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = activity.formattedTime,
                fontSize = 12.sp,
                color = TextWhite
            )
        }
    }
}