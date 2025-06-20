package eu.tutorials.socialnetwork.presentaion.main_feed

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.domain.models.Post

@Composable
fun MainFeedScreen(
    navController: NavController
){
    eu.tutorials.socialnetwork.presentaion.components.Post(
        Post(
            username = "Pranav Waghmore",
            imageUrl = "",
            postPictureUrl = "",
            description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed" +
                    "diam nonumy eirmod tempor invidunt ut labore dolore " +
                    "magna aliquyam erat,seddiam end...",
            likeCount = 17,
            commentCount = 7
        ) ,
        modifier = Modifier
    )
}