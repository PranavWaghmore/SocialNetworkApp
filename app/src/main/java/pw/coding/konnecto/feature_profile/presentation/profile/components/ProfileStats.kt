package pw.coding.konnecto.feature_profile.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.User
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace

@Composable
fun ProfileStats(
    user: User,
    modifier: Modifier = Modifier,
    isOwnProfile: Boolean = true,
    isFollowing : Boolean = false,
    onFollowClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ProfileNumber(
            number = user.onFollowersCount,
            text = stringResource(R.string.followers)
        )
        Spacer(Modifier.width(LargeSpace))
        ProfileNumber(
            number = user.onFollowingCount,
            text = stringResource(R.string.following)
        )
        Spacer(Modifier.width(LargeSpace))
        ProfileNumber(
            number = user.onPostCount,
            text = stringResource(R.string.posts)
        )
        if (isOwnProfile) {
            Spacer(Modifier.width(LargeSpace))
            Button(
                onClick = onFollowClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isFollowing) {
                        MaterialTheme.colorScheme.primary
                    } else Color.Red
                ),
                elevation = ButtonDefaults.buttonElevation(5.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                     text = if (isFollowing){
                         stringResource(R.string.unfollow)
                    } else {
                         stringResource(R.string.follow)
                    } ,
                    color = if(isFollowing){
                        Color.White
                    }else MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}