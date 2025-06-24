package eu.tutorials.socialnetwork.presentaion.main_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.domain.models.Post
import eu.tutorials.socialnetwork.presentaion.components.StandardToolBar
import eu.tutorials.socialnetwork.presentaion.util.Screen

@Composable
fun MainFeedScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
          title = {
              Text(
                  "Main Feed" ,
                  color = Color.White
              )
          } ,
            showBackArrow = false,
            navController = navController,
            navActions = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        Icons.Outlined.Search ,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        )
        eu.tutorials.socialnetwork.presentaion.components.Post(
            Post(
                username = "Pranav Waghmore",
                imageUrl = "",
                postPictureUrl = "",
                description = "Lorem ipsum dolor sit amet, consecrate disciplining elite, sed" +
                        "diam nonnull usermod temper invidious ut labor do lore " +
                        "magna aliquot erat,sediment end...",
                likeCount = 17,
                commentCount = 7
            ),
            modifier = Modifier ,
            onClick = {
                navController.navigate(Screen.PostDetailScreen.route)
            }
        )
    }
}
