package pw.coding.konnecto.presentaion.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pw.coding.konnecto.R
import pw.coding.konnecto.domain.models.Post
import pw.coding.konnecto.presentaion.ui.theme.ExtraSmallSpace
import pw.coding.konnecto.presentaion.ui.theme.LightGray
import pw.coding.konnecto.presentaion.ui.theme.MediumSpace
import pw.coding.konnecto.presentaion.ui.theme.SmallSpace
import pw.coding.konnecto.presentaion.ui.theme.TextGray
import pw.coding.konnecto.presentaion.ui.theme.TextWhite
import pw.coding.konnecto.util.Constants

@Composable
fun Post(
    post: Post,
    showProfileImage: Boolean =true,
    modifier: Modifier= Modifier,
    profilePictureDpSize: Dp = 40.dp,
    onClick :() -> Unit ={}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(MediumSpace)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .clickable(
                    onClick = onClick
                )
        ) {
            Image(
                painter =painterResource(R.drawable.marvel),
                contentDescription = "Post image",
                modifier = Modifier
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ExtraSmallSpace)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(showProfileImage){
                        Image(
                            painterResource(R.drawable.pranav),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(profilePictureDpSize)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    ActionRow(
                        username = "Pranav Waghmore",
                        modifier = Modifier.fillMaxWidth(),
                        onLikeClick = { isLiked ->
                        },
                        onCommentClick = {

                        },
                        onShareClick = {

                        },
                        onUsernameClick = { username ->
                        }
                    )
                }
                Spacer(modifier = Modifier.height(SmallSpace))
                Text(
                    text =
                        buildAnnotatedString {
                            append(post.description)
                            withStyle(
                                SpanStyle(
                                    color = TextGray
                                )
                            ){
                                append(
                                    LocalContext.current.getString(
                                        R.string.read_more
                                    )
                                )
                            }
                        },
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = Constants.MAX_POST_DESCRIPTION_LINES
                )
                Spacer(modifier = Modifier.height(MediumSpace))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(
                            R.string.liked_by_x_people,
                            post.likeCount
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(
                            R.string.x_comments,
                            post.commentCount
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
@Composable
fun EngagementButtons(
    modifier: Modifier = Modifier,
    iconSize: Dp = 30.dp,
    isLiked: Boolean = false,
    onLikeClick: (Boolean) -> Unit,
    onCommentClick: (Boolean) -> Unit,
    onShareClick: (Boolean) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        IconButton(
            onClick = {
                onLikeClick(!isLiked)
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = if (isLiked) {
                    Color.Red
                } else {
                    TextWhite
                },
                contentDescription = if (isLiked) {
                    stringResource(R.string.unlike)
                } else {
                    stringResource(R.string.like)
                }
            )
        }
        Spacer(modifier = Modifier.width(SmallSpace))
        IconButton(
            onClick = {
                onCommentClick
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Comment,
                tint = Color.White,
                contentDescription = stringResource(R.string.comment)
            )
        }
        Spacer(modifier = Modifier.width(SmallSpace))
        IconButton(
            onClick = {
                onShareClick
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                tint = Color.White,
                contentDescription = stringResource(R.string.share)
            )
        }
    }
}

@Composable
fun ActionRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLikeClick: (Boolean) -> Unit,
    onCommentClick: (Boolean) -> Unit,
    onShareClick: (Boolean) -> Unit,
    username: String,
    onUsernameClick: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = username,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .clickable(
                        onClick = {
                            onUsernameClick(username)
                        }
                    )
            ) }
        EngagementButtons(
            modifier = Modifier,
            isLiked = isLiked,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
            onShareClick = onShareClick
        )
    }
}