package eu.tutorials.socialnetwork.presentaion.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = GreenAccent,
    background = DarkGrey,
    onBackground = TextWhite,
    onPrimary = DarkGrey,
    surface = MediumGrey,
    onSurface = LightGray
)

@Composable
fun SocialNetworkTheme(
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}