package pw.coding.konnecto.feature_profile.presentation.profile.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import pw.coding.konnecto.R
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.presentation.ui.theme.SmallSpace
import pw.coding.konnecto.core.util.toPx
import pw.coding.konnecto.feature_profile.domain.model.Skill

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BannerSection(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    iconSize: Dp = 35.dp,
    skillIconSize: Dp = 28.dp,
    leftIconModifier: Modifier = Modifier,
    rightIconModifier: Modifier = Modifier,
    topSkills: List<Skill> = emptyList(),
    showGitHub: Boolean,
    showInstagram: Boolean,
    showLinkedIn: Boolean,
    onGithubClick: () -> Unit = {},
    onInstagramClick: () -> Unit = {},
    onLeetCodeClick: () -> Unit = {},
    bannerUrl: String? = null
) {
    val context = LocalContext.current
    BoxWithConstraints(
        modifier = modifier
    ) {
        AsyncImage(
            model = bannerUrl,
            contentDescription = stringResource(R.string.banner),
            contentScale = ContentScale.Crop,
            modifier = imageModifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, Color.Black
                        ), startY = constraints.maxHeight - iconSize.toPx() * 2f
                    )
                )
        )
        Row(
            modifier = leftIconModifier
                .height(iconSize)
                .align(Alignment.BottomStart)
                .padding(SmallSpace),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(SmallSpace))

            topSkills.take(3).forEach { topSkill ->
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(topSkill.imageUrl)
                        .crossfade(true)
                        .build(),
                    imageLoader = ImageLoader.Builder(context)
                        .components {
                            add(SvgDecoder.Factory())
                        }
                        .build(),
                    contentDescription = stringResource(R.string.skill),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(skillIconSize)
                )

                Spacer(modifier = Modifier.width(MediumSpace))
            }
        }
        Row(
            modifier = rightIconModifier
                .height(iconSize)
                .align(Alignment.BottomEnd)
                .padding(SmallSpace)
        ) {
            if (showGitHub) {
                IconButton(
                    onClick = onGithubClick, modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(R.drawable.github_icon_1),
                        contentDescription = "github",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
            if (showInstagram) {
                IconButton(
                    onClick = onLeetCodeClick, modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(R.drawable.instagram_2016_5),
                        contentDescription = "instagram",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
            if (showLinkedIn) {
                IconButton(
                    onClick = onInstagramClick, modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(R.drawable.linkedin_icon_1),
                        contentDescription = "linkedin",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        }
    }

}

