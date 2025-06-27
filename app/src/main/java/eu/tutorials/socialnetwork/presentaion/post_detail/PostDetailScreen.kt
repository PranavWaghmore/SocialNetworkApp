package eu.tutorials.socialnetwork.presentaion.post_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import eu.tutorials.socialnetwork.domain.models.Comment
import eu.tutorials.socialnetwork.domain.models.Post
import eu.tutorials.socialnetwork.presentaion.components.ActionRow
import eu.tutorials.socialnetwork.presentaion.components.StandardToolBar
import eu.tutorials.socialnetwork.presentaion.ui.theme.DarkGrey
import eu.tutorials.socialnetwork.presentaion.ui.theme.ExtraSmallSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.LargeSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.MediumSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.ProfilePictureDpSize
import eu.tutorials.socialnetwork.presentaion.ui.theme.SmallSpace

@Composable
fun PostDetailScreen(
    navController: NavController,
    post: Post
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            navController = navController,
            title = {
                Text(
                    text = "Post Detail Screen",
                    color = Color.White
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGrey)
        ) {
            item {
                // Fixed post content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Image(
                        painter = painterResource(R.drawable.marvel),
                        contentDescription = "Post image",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(ExtraSmallSpace)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.pranav),
                                contentDescription = "Profile picture",
                                modifier = Modifier
                                    .size(ProfilePictureDpSize)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            ActionRow(
                                username = "Pranav Waghmore",
                                modifier = Modifier.fillMaxWidth(),
                                onLikeClick = {},
                                onCommentClick = {},
                                onShareClick = {},
                                onUsernameClick = {}
                            )
                        }
                        Spacer(modifier = Modifier.height(SmallSpace))
                        Text(
                            text = post.description,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(MediumSpace))
                        Text(
                            text = stringResource(
                                id = R.string.liked_by_x_people,
                                post.likeCount
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(LargeSpace))
                }
            }
            items(20) { index ->
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = SmallSpace),
                    comment = Comment(
                        username = "Pranav Waghmore $index",
                        comment = "Not just another post — it's a reflection of moments, memories, and milestones."+
                        "Every image holds a story, and this one is a piece of my journey."
                    ),
                    onLikeClick = { /* Handle like click */ }
                )
            }
        }
    }
}

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    comment: Comment = Comment(),
    onLikeClick: (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(MediumSpace)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.pranav),
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(25.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = comment.username,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "2 days ago",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(MediumSpace))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = comment.comment,
                    color = Color.White,
                    modifier = Modifier.weight(9f)
                )
                Spacer(modifier = Modifier.width(MediumSpace))
                IconButton(
                    onClick = { onLikeClick(comment.isLiked) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = if (comment.isLiked) "Unlike" else "Like",
                        tint = if (comment.isLiked) Color.Red else Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(MediumSpace))

            Text(
                text = stringResource(R.string.liked_by_x_people, 2),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
