package eu.tutorials.socialnetwork.presentaion.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.domain.models.Post
import eu.tutorials.socialnetwork.domain.models.User
import eu.tutorials.socialnetwork.presentaion.components.StandardToolBar
import eu.tutorials.socialnetwork.presentaion.profile.components.BannerSection
import eu.tutorials.socialnetwork.presentaion.profile.components.ProfileHeaderSection
import eu.tutorials.socialnetwork.presentaion.ui.theme.ProfilePictureDpSizeLarge
import eu.tutorials.socialnetwork.presentaion.ui.theme.SmallSpace
import eu.tutorials.socialnetwork.presentaion.util.Screen

@Composable
fun ProfileScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(R.string.your_profile)
                )
            },
            navActions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More options",
                        tint = Color.White
                    )
                }
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                BannerSection(
                    modifier = Modifier.height(140.dp)
                )
            }
            item {
                ProfileHeaderSection(
                    user = User(
                        profilePictureUrl = "",
                        username = "Pranav Waghmore",
                        description = "Passionate Android developer crafting intuitive and" +
                                " modern mobile experiences."
                    ),
                    modifier = Modifier.fillMaxSize(),
                   onEditClick = {}
                )
            }
            items(20) {
                Spacer(modifier = Modifier.height(SmallSpace))
                eu.tutorials.socialnetwork.presentaion.components.Post(
                    post =  Post(
                        username = "Pranav Waghmore",
                        imageUrl = "",
                        postPictureUrl = "",
                        description = "Not just another post, but a piece of my journey...",
                        likeCount = 17,
                        commentCount = 7
                    ),
                    modifier = Modifier.offset( y=-(ProfilePictureDpSizeLarge/2f)),
                    onClick = {
                        navController.navigate(Screen.PostDetailScreen.route)
                    }
                )
            }
        }
    }
}