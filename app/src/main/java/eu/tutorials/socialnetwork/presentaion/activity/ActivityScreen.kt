package eu.tutorials.socialnetwork.presentaion.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.domain.models.Activity
import eu.tutorials.socialnetwork.domain.models.Comment
import eu.tutorials.socialnetwork.domain.util.ActivityAction
import eu.tutorials.socialnetwork.domain.util.DateFormatUtil
import eu.tutorials.socialnetwork.presentaion.components.ActionRow
import eu.tutorials.socialnetwork.presentaion.components.StandardToolBar
import eu.tutorials.socialnetwork.presentaion.post_detail.Comment
import eu.tutorials.socialnetwork.presentaion.ui.theme.DarkGrey
import eu.tutorials.socialnetwork.presentaion.ui.theme.ExtraSmallSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.LargeSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.MediumSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.ProfilePictureDpSize
import eu.tutorials.socialnetwork.presentaion.ui.theme.SmallSpace
import kotlin.random.Random

@Composable
fun ActivityScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            navController = navController,
            title = {
                Text(
                    text = "Your Activity",
                    color = Color.White
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGrey),
            contentPadding = PaddingValues(MediumSpace)
        ) {
            items(20) {
                ActivityItem(
                    modifier = Modifier,
                    activity = Activity(
                        username = "Pranav Waghmore",
                        actionType = if (Random.nextInt(2) == 0) {
                            ActivityAction.LikedPost
                        } else ActivityAction.CommentedOnPost,
                        formattedTime = DateFormatUtil
                            .timestampToFormattedString(
                                timestamp = System.currentTimeMillis(),
                                pattern = "MMM dd, HH:mm"
                            )
                    )
                )
                if(it <19){
                    Spacer(modifier = Modifier.height(MediumSpace ))
                }
            }
        }
    }
}