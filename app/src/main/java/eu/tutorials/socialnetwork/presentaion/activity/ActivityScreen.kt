package eu.tutorials.socialnetwork.presentaion.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.domain.models.Activity
import eu.tutorials.socialnetwork.domain.util.ActivityAction
import eu.tutorials.socialnetwork.domain.util.DateFormatUtil
import eu.tutorials.socialnetwork.presentaion.activity.components.ActivityItem
import eu.tutorials.socialnetwork.presentaion.components.StandardToolBar
import eu.tutorials.socialnetwork.presentaion.ui.theme.DarkGrey
import eu.tutorials.socialnetwork.presentaion.ui.theme.MediumSpace
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