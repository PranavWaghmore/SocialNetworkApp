package pw.coding.konnecto.feature_post.presentation.main_feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import pw.coding.konnecto.core.presentation.components.Post
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.util.Screen

@Composable
fun MainFeedScreen(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    viewModel: MainFeedViewModel = hiltViewModel()
) {
    val posts = viewModel.posts.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val state = viewModel.state.value
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            title = {
                Text(
                    "Main Feed",
                    color = Color.White
                )
            },
            showBackArrow = false,
            navController = navController,
            navActions = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if(state.isLoadingFirstTime){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn {
                items(posts.itemCount) { i ->
                    val post = posts[i]
                    if (post != null) {
                        Post(
                            post = post,
                            showProfileImage = false,
                            onClick = {
                                navController.navigate(Screen.PostDetailScreen.route)
                            }
                        )
                    }
                }
                item {
                    if (state.isLoadingNewPost) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
                posts.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvent.LoadedPage)
                        }

                        loadState.append is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvent.LoadMorePosts)
                        }

                        loadState.append is LoadState.NotLoading -> {
                            viewModel.onEvent(MainFeedEvent.LoadedPage)
                        }

                        loadState.append is LoadState.Error -> {
                            scope.launch {
                                snackBarHostState.showSnackbar(
                                    message = "Error Loading more posts"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
