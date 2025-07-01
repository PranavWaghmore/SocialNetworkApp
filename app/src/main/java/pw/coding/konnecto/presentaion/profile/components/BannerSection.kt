package pw.coding.konnecto.presentaion.profile.components

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
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pw.coding.konnecto.R
import pw.coding.konnecto.presentaion.ui.theme.MediumSpace
import pw.coding.konnecto.presentaion.ui.theme.SmallSpace
import pw.coding.konnecto.presentaion.util.toPx

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BannerSection(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    iconSize: Dp = 35.dp,
    leftIconModifier: Modifier = Modifier,
    rightIconModifier: Modifier = Modifier,
    onGithubClick: () -> Unit = {},
    onInstagramClick: () -> Unit = {},
    onLeetCodeClick: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.channelart),
            contentDescription = stringResource(R.string.banner),
            contentScale = ContentScale.Crop,
            modifier = imageModifier
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = constraints.maxHeight - iconSize.toPx() * 2f
                    )
                )
        )
        Row(
            modifier = leftIconModifier
                .height(iconSize)
                .align((Alignment.BottomStart))
                .padding(SmallSpace),
        ) {
            Spacer(modifier = Modifier.width(SmallSpace))
            Image(
                painter = painterResource(R.drawable.c_),
                contentDescription = "c#",
                modifier = Modifier.height(iconSize),
            )
            Spacer(modifier = Modifier.width(MediumSpace))
            Image(
                painter = painterResource(R.drawable.android_logo_2),
                contentDescription = "java",
                modifier = Modifier.height(iconSize),
            )
            Spacer(modifier = Modifier.width(MediumSpace))
            Image(
                painter = painterResource(R.drawable.kotlin_1),
                contentDescription = "kotlin",
                modifier = Modifier.height(iconSize),
            )
        }
        Row(
            modifier = rightIconModifier
                .height(iconSize)
                .align(Alignment.BottomEnd)
                .padding(SmallSpace)
        ) {
            IconButton(
                onClick = onGithubClick,
                modifier = Modifier.size(iconSize)
            ) {
                Image(
                    painter = painterResource(R.drawable.github_icon_1),
                    contentDescription = "github",
                    modifier = Modifier.size(iconSize)
                )
            }
            IconButton(
                onClick = onLeetCodeClick,
                modifier = Modifier.size(iconSize)
            ) {
                Image(
                    painter = painterResource(R.drawable.instagram_2016_5),
                    contentDescription = "instagram",
                    modifier = Modifier.size(iconSize)
                )
            }
            IconButton(
                onClick = onInstagramClick,
                modifier = Modifier.size(iconSize)
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