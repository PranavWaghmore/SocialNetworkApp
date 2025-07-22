package pw.coding.konnecto.presentaion.activity.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pw.coding.konnecto.R
import pw.coding.konnecto.domain.models.Activity
import pw.coding.konnecto.domain.util.ActivityAction
import pw.coding.konnecto.presentaion.ui.theme.SmallSpace
import pw.coding.konnecto.presentaion.ui.theme.TextWhite

@Composable
fun ActivityItem(
    modifier: Modifier,
    activity: Activity
){
    Card (
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(SmallSpace),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            val fillerText = when (activity.actionType) {
                is ActivityAction.LikedPost ->
                    stringResource(id = R.string.liked)
                is ActivityAction.CommentedOnPost ->
                    stringResource(id = R.string.commented_on)
                is ActivityAction.FollowedYou ->
                    stringResource(id = R.string.followed_you)
            }
            val actionText= when ( activity.actionType){
                is ActivityAction.LikedPost ->
                    stringResource(R.string.you_post)
                is ActivityAction.CommentedOnPost ->
                    stringResource(R.string.you_post)
                is ActivityAction.FollowedYou -> ""
            }
            Text(
                text = buildAnnotatedString {
                    val boldStyle = SpanStyle(
                       fontWeight = FontWeight.Bold
                    )
                    withStyle(boldStyle) { append(activity.username) }
                    append(" $fillerText")
                    append(" ")
                    withStyle(boldStyle) { append(actionText) }
                },
                fontSize = 12.sp,
                color = TextWhite
            )
            Text(
                text=activity.formattedTime,
                fontSize = 12.sp,
                color = TextWhite
            )
        }
    }
}