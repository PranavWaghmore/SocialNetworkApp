package pw.coding.konnecto.feature_post.presentation.main_feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pw.coding.konnecto.R
import pw.coding.konnecto.core.presentation.components.Post
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.presentation.util.asString
import pw.coding.konnecto.core.util.Screen
import pw.coding.konnecto.core.util.sendSharePost

@Composable
fun MainFeedScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: MainFeedViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
) {

    val pagingState = viewModel.pagingState.value
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
           when(event){
               is UiEvent.ShowSnackbar ->{
                   snackBarHostState.showSnackbar(
                       message = event.uiText.asString(context)
                   )
               }
               else -> {}
           }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            title = {
                Text(
                    stringResource(R.string.main_feed),
                    color = Color.White
                )
            },
            showBackArrow = false,
            onNavigateUp = onNavigateUp,
            navActions = {
                IconButton(
                    onClick = {
                        onNavigate(Screen.SearchScreen.route)
                    }
                ) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = MediumSpace)
        ) {
            LazyColumn {
                items(
                    count = pagingState.items.size
                ) { i ->
                    val post = pagingState.items[i]
                    if (i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                        viewModel.loadNextItems()
                    }
                    Post(
                        post = post,
                        showProfileImage = true,
                        onPostClick = {
                           onNavigate(Screen.PostDetailScreen.route + "/${post.id}")
                        },
                        onLikeClick = {
                            viewModel.onEvent(MainFeedEvent.OnPostLiked(post.id))
                        },
                        onUsernameClick = {
                            onNavigate(Screen.ProfileScreen.route + "?userId=${post.userId}")
                        },
                        onCommentClick = {
                            onNavigate(Screen.PostDetailScreen.route +
                                    "/${post.id}?shouldShowKeyboard=true")
                        },
                        onShareClick = {
                            context.sendSharePost(post.id)
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(94.dp))
                }
            }
            if (pagingState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
