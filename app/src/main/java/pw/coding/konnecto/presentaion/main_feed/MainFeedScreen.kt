package pw.coding.konnecto.presentaion.main_feed

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
import pw.coding.konnecto.domain.models.Post
import pw.coding.konnecto.presentaion.components.StandardToolBar
import pw.coding.konnecto.presentaion.util.Screen

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
        pw.coding.konnecto.presentaion.components.Post(
           post =  Post(
                username = "Pranav Waghmore",
                imageUrl = "",
                postPictureUrl = "",
                description = "Not just another post, but a piece of my journey...",
                likeCount = 17,
                commentCount = 7
            ),
            modifier = Modifier,
            onClick = {
                navController.navigate(Screen.PostDetailScreen.route)
            }
        )
    }
}
