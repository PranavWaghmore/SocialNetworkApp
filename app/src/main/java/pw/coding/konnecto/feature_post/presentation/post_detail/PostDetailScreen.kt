package pw.coding.konnecto.feature_post.presentation.post_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import pw.coding.konnecto.R
import pw.coding.konnecto.core.presentation.components.ActionRow
import pw.coding.konnecto.core.presentation.components.StandardTextField
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.ui.theme.DarkGrey
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.presentation.ui.theme.ProfilePictureDpSize
import pw.coding.konnecto.core.presentation.ui.theme.SmallSpace
import pw.coding.konnecto.core.presentation.ui.theme.TextWhite
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.presentation.util.asString
import pw.coding.konnecto.core.util.Screen
import pw.coding.konnecto.core.util.sendSharePost

@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    snackBarHostState: SnackbarHostState,
    shouldShowKeyBoard: Boolean = false
) {
    val state = viewModel.state.value

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        if(shouldShowKeyBoard){
            focusRequester.requestFocus()
            keyboardController?.show()
        }
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.uiText.asString(context))
                }
                else -> {}
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            onNavigateUp = onNavigateUp,
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
                .weight(1f)
                .fillMaxWidth()
                .background(DarkGrey)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            state.post?.let { post ->
                                AsyncImage(
                                    model = post.imageUrl,
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "Post image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f),
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(LargeSpace)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AsyncImage(
                                            model = state.post.profilePictureUrl,
                                            contentDescription = "Profile picture",
                                            modifier = Modifier
                                                .size(ProfilePictureDpSize)
                                                .clip(CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                        ActionRow(
                                            username = state.post.username,
                                            modifier = Modifier.fillMaxWidth(),
                                            onLikeClick = {
                                                viewModel.onEvent(
                                                    PostDetailEvent.LikePost(post.id)
                                                )
                                            },
                                            onCommentClick = {
                                                focusRequester.requestFocus()
                                                keyboardController?.show()
                                            },
                                            onShareClick = {
                                                context.sendSharePost(post.id)
                                            },
                                            onUsernameClick = {
                                                onNavigate(Screen.ProfileScreen.route + "?userId=${post.userId}")
                                            },
                                            isLiked = state.post.isLiked
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(SmallSpace))
                                    Text(
                                        text = state.post.description,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.height(MediumSpace))
                                    Text(
                                        text = stringResource(
                                            id = R.string.liked_by_x_people,
                                            post.likeCount,
                                        ),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.clickable(
                                            enabled = post.likeCount > 0,
                                            onClick = {
                                                onNavigate(Screen.PersonListScreen.route + "/${post.id}")
                                            }
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(LargeSpace))
                        }
                        if (state.isLoadingPost) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
            items(
                count = state.comments.size,
                key = { i -> state.comments[i].id }
            ) { i ->
                val comment = state.comments[i]
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = SmallSpace,
                            horizontal = MediumSpace
                        ),
                    comment = comment,
                    onLikeClick = { _ ->
                        viewModel.onEvent(PostDetailEvent.LikeComment(comment.id))
                    },
                    isLiked = comment.isLiked,
                    onLikedByClick = {
                       onNavigate(Screen.PersonListScreen.route + "/${comment.id}")
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(LargeSpace),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StandardTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                text = viewModel.commentTextFieldState.value.text,
                hint = stringResource(R.string.enter_comment),
                onValueChange = {
                    viewModel.onEvent(
                        PostDetailEvent.EnteredComment(comment = it)
                    )
                }
            )
            Spacer(modifier = Modifier.width(SmallSpace))


            IconButton(
                onClick = {
                    viewModel.onEvent(PostDetailEvent.Comment)
                },
                colors = IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Black,
                    disabledContentColor = Black,
                    disabledContainerColor = Black
                )
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send Comment",
                    tint = TextWhite
                )
            }
        }
    }
}

