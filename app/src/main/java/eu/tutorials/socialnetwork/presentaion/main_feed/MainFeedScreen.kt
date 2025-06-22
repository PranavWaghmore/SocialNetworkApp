package eu.tutorials.socialnetwork.presentaion.main_feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.domain.models.Post
import eu.tutorials.socialnetwork.presentaion.components.StandardScaffold

@Composable
fun MainFeedScreen(
    navController: NavController
) {
    StandardScaffold(
        navController = navController,
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            eu.tutorials.socialnetwork.presentaion.components.Post(
                Post(
                    image = painterResource(R.drawable.marvel),
                    username = "Pranav Waghmore",
                    imageUrl = "",
                    postPictureUrl = "",
                    description = "Lorem ipsum dolor sit amet, consecrate disciplining elite, sed" +
                            "diam nonnull usermod temper invidious ut labor do lore " +
                            "magna aliquot erat,sediment end...",
                    likeCount = 17,
                    commentCount = 7
                ),
                modifier = Modifier
            )
            eu.tutorials.socialnetwork.presentaion.components.Post(
                Post(
                    image = painterResource(R.drawable.spiderman),
                    username = "Pranav Waghmore",
                    imageUrl = "",
                    postPictureUrl = "",
                    description = "Lorem ipsum dolor sit amet, consecrate disciplining elite, sed" +
                            "diam nonnull usermod temper invidious ut labor do lore " +
                            "magna aliquot erat,sediment end...",
                    likeCount = 17,
                    commentCount = 7
                ),
                modifier = Modifier
            )
            eu.tutorials.socialnetwork.presentaion.components.Post(
                Post(
                    image = painterResource(R.drawable.naruto),
                    username = "Pranav Waghmore",
                    imageUrl = "",
                    postPictureUrl = "",
                    description = "Lorem ipsum dolor sit amet, consecrate disciplining elite, sed" +
                            "diam nonnull usermod temper invidious ut labor do lore " +
                            "magna aliquot erat,sediment end...",
                    likeCount = 17,
                    commentCount = 7
                ),
                modifier = Modifier
            )
        }
    }
}