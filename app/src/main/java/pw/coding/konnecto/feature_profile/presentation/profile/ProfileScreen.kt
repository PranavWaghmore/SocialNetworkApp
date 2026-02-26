package pw.coding.konnecto.feature_profile.presentation.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.domain.models.User
import pw.coding.konnecto.core.presentation.components.Post
import pw.coding.konnecto.core.presentation.ui.theme.ProfilePictureDpSizeLarge
import pw.coding.konnecto.core.presentation.ui.theme.SmallSpace
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.presentation.util.asString
import pw.coding.konnecto.core.util.Screen
import pw.coding.konnecto.core.util.toPx
import pw.coding.konnecto.feature_profile.presentation.profile.components.BannerSection
import pw.coding.konnecto.feature_profile.presentation.profile.components.ProfileHeaderSection

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    profilePictureSize: Dp = ProfilePictureDpSizeLarge,
    snackBarHostState: SnackbarHostState
) {
    val toolbarState = viewModel.toolbarState.value
    val iconHorizontalCentreLength =
        (LocalConfiguration.current.screenWidthDp.dp.toPx() / 4f -
                (profilePictureSize / 4f).toPx() - SmallSpace.toPx()) / 2

    val iconSizeExpanded = 35.dp
    val lazyListState = rememberLazyListState()
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    val toolbarHeightCollapsed = 75.dp
    val imageCollapsedOffset = remember {
        (toolbarHeightCollapsed - profilePictureSize / 2f) / 2f
    }
    val iconCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - iconSizeExpanded) / 2f
    }
    val toolbarExpandedHeight = remember {
        bannerHeight + profilePictureSize
    }
    val maxOffset = remember {
        toolbarExpandedHeight - toolbarHeightCollapsed
    }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if (delta > 0f && lazyListState.firstVisibleItemIndex != 0) {
                    return Offset.Zero
                }
                val newOffset = viewModel.toolbarState.value.toolbarOffsetY + delta
                viewModel.setToolbarOffset(
                    newOffset.coerceIn(
                        minimumValue = -maxOffset.toPx(),
                        maximumValue = 0f
                    )
                )
                viewModel.setExpandedRatio(
                    (viewModel.toolbarState.value.toolbarOffsetY + maxOffset.toPx()) / maxOffset.toPx()
                )
                return Offset.Zero
            }
        }
    }
    val state = viewModel.state.value
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {

                is UiEvent.SnackBarEvent -> {
                    snackBarHostState.showSnackbar(
                        message = event.snackBarUiText.asString(context)
                    )
                }

                else -> {}
            }
        }
    }

    when {
        state.isLoading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize(),
                color = Color.Red
            )
            Text("IsLoading")
            return
        }

        state.profile == null -> {
            CircularProgressIndicator(
                modifier = Modifier
                .fillMaxSize(),
                color = Color.Red
            )
            Text("Profile null")
            return
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState
        ) {
            item {
                Spacer(
                    modifier = Modifier.height(
                        toolbarExpandedHeight - profilePictureSize / 2f
                    )
                )
            }
            item {
                state.profile?.let { profile ->
                    ProfileHeaderSection(
                        user = User(
                            userId = profile.userId,
                            profilePictureUrl = profile.profilePictureUrl,
                            username = profile.username,
                            description = profile.bio,
                            followersCount = profile.followerCount,
                            followingCount = profile.followingCount,
                            postCount = profile.postCount
                        ),
                        isOwnProfile = profile.isOwnProfile,
                        modifier = Modifier.fillMaxSize(),
                        onEditClick = {
                            navController.navigate(Screen.EditProfileScreen.route)
                        }
                    )

                }
            }
            items(5) {
                Spacer(modifier = Modifier.height(SmallSpace))
                Post(
                    post = Post(
                        username = "Pranav Waghmore",
                        imageUrl = "",
                        description = "Not just another post, but a piece of my journey...",
                        likeCount = 17,
                        commentCount = 7,
                        isLiked = false,
                        isOwnPost = true,
                        id = "",
                        profilePictureUrl = "",
                        userId = ""
                    ),
                    onClick = {
                        navController.navigate(Screen.PostDetailScreen.route)
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            state.profile?.let { profile ->
                BannerSection(
                    modifier = Modifier.height(
                        (bannerHeight * toolbarState.expandedRatio).coerceIn(
                            minimumValue = toolbarHeightCollapsed,
                            maximumValue = bannerHeight
                        )
                    ),
                    leftIconModifier = Modifier.graphicsLayer {
                        translationY =
                            (1f - toolbarState.expandedRatio) * -iconCollapsedOffsetY.toPx()
                        translationX =
                            (1f - toolbarState.expandedRatio) * iconHorizontalCentreLength
                    },
                    rightIconModifier = Modifier.graphicsLayer {
                        translationY =
                            (1f - toolbarState.expandedRatio) * -iconCollapsedOffsetY.toPx()
                        translationX =
                            (1f - toolbarState.expandedRatio) * -iconHorizontalCentreLength
                    },
                    showGitHub = profile.gitHubUrl != null,
                    showInstagram = profile.instagramUrl != null,
                    showLinkedIn = profile.linkedInUrl != null,
                    bannerUrl = profile.bannerUrl

                )
                AsyncImage(
                    model = profile.profilePictureUrl,
                    contentDescription = stringResource(R.string.profile),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .graphicsLayer {
                            translationY = (-profilePictureSize.toPx() / 2f -
                                    (1 - toolbarState.expandedRatio) * imageCollapsedOffset.toPx())
                            transformOrigin = TransformOrigin(
                                pivotFractionX = 0.5f,
                                pivotFractionY = 0f
                            )
                            val scale = 0.5f + toolbarState.expandedRatio * 0.5f
                            scaleX = scale
                            scaleY = scale
                        }
                        .size(profilePictureSize)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        )
                )
            }

        }
    }
}
