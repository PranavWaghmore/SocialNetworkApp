package eu.tutorials.socialnetwork.presentaion.profile

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.domain.models.Post
import eu.tutorials.socialnetwork.domain.models.User
import eu.tutorials.socialnetwork.presentaion.profile.components.BannerSection
import eu.tutorials.socialnetwork.presentaion.profile.components.ProfileHeaderSection
import eu.tutorials.socialnetwork.presentaion.ui.theme.ProfilePictureDpSizeLarge
import eu.tutorials.socialnetwork.presentaion.ui.theme.SmallSpace
import eu.tutorials.socialnetwork.presentaion.util.Screen
import eu.tutorials.socialnetwork.presentaion.util.toPx

@Composable
fun ProfileScreen(
    navController: NavController,
    profilePictureSize: Dp = ProfilePictureDpSizeLarge,
) {
    var toolbarOffsetY by remember {
        mutableStateOf(0f)
    }
    val iconHorizontalCentreLength =
        (LocalConfiguration.current.screenWidthDp.dp.toPx() / 4f -
                (profilePictureSize / 4f).toPx() - SmallSpace.toPx()) / 2

    val iconSizeExpanded = 35.dp
    val lazyListState = rememberLazyListState()
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    val toolbarHeightCollapsed = 75.dp
    var imageCollapsedOffset = remember {
        (toolbarHeightCollapsed - profilePictureSize / 2f) / 2f
    }
    var iconCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - iconSizeExpanded) / 2f
    }
    val toolbarExpandedHeight = remember {
        bannerHeight + profilePictureSize
    }
    val maxOffset = remember {
        toolbarExpandedHeight - toolbarHeightCollapsed
    }
    var expandedRatio by remember {
        mutableStateOf(1f)
    }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if (delta > 0f && lazyListState.firstVisibleItemIndex != 0) {
                    return Offset.Zero
                }
                val newOffset = toolbarOffsetY + delta
                toolbarOffsetY = newOffset.coerceIn(
                    minimumValue = -maxOffset.toPx(),
                    maximumValue = 0f
                )
                expandedRatio = (toolbarOffsetY + maxOffset.toPx()) / maxOffset.toPx()
                return Offset.Zero
            }
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
                ProfileHeaderSection(
                    user = User(
                        profilePictureUrl = "",
                        username = "Pranav Waghmore",
                        description = "Passionate Android developer crafting intuitive and" +
                                " modern mobile experiences."
                    ),
                    modifier = Modifier.fillMaxSize(),
                    onEditClick = {
                        navController.navigate(Screen.EditProfileScreen.route)
                    }
                )
            }
            items(20) {
                Spacer(modifier = Modifier.height(SmallSpace))
                eu.tutorials.socialnetwork.presentaion.components.Post(
                    Post(
                        username = "Pranav Waghmore",
                        imageUrl = "",
                        postPictureUrl = "",
                        description = "Not just another post, but a piece of my journey...",
                        likeCount = 17,
                        commentCount = 7
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
            BannerSection(
                modifier = Modifier.height(
                    (bannerHeight * expandedRatio).coerceIn(
                        minimumValue = toolbarHeightCollapsed,
                        maximumValue = bannerHeight
                    )
                ),
                leftIconModifier = Modifier.graphicsLayer {
                    translationY = (1f - expandedRatio) * -iconCollapsedOffsetY.toPx()
                    translationX = (1f - expandedRatio) * iconHorizontalCentreLength
                },
                rightIconModifier = Modifier.graphicsLayer {
                    translationY = (1f - expandedRatio) * -iconCollapsedOffsetY.toPx()
                    translationX = (1f - expandedRatio) * -iconHorizontalCentreLength
                }
            )
            Image(
                painter = painterResource(R.drawable.pranav),
                contentDescription = stringResource(R.string.profile),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .graphicsLayer {
                        translationY = (-profilePictureSize.toPx() / 2f -
                                (1 - expandedRatio) * imageCollapsedOffset.toPx())
                        transformOrigin = TransformOrigin(
                            pivotFractionX = 0.5f,
                            pivotFractionY = 0f
                        )
                        val scale = 0.5f + expandedRatio * 0.5f
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