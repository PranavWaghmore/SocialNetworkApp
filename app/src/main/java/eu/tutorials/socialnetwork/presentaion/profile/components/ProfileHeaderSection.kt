package eu.tutorials.socialnetwork.presentaion.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.domain.models.User
import eu.tutorials.socialnetwork.presentaion.ui.theme.LargeSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.MediumSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.ProfilePictureDpSizeLarge
import eu.tutorials.socialnetwork.presentaion.ui.theme.SmallSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.TextWhite

@Composable
fun ProfileHeaderSection(
    user: User,
    modifier: Modifier,
    isOwnProfile : Boolean = true,
    onEditClick : () ->  Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset( y=-(ProfilePictureDpSizeLarge/2f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.pranav),
            contentDescription = stringResource(R.string.profile),
            modifier = Modifier
                .clip(CircleShape)
                .size(ProfilePictureDpSizeLarge)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.height(SmallSpace))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .offset(
                    x= if(isOwnProfile){
                        (35.dp + SmallSpace)/2f
                    }else 0.dp
                )
        ) {
            Text(
                text = user.username,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 24.sp
                )
            )
            if(isOwnProfile){
                Spacer(Modifier.width(SmallSpace))
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit ,
                        contentDescription = stringResource(R.string.edit),
                        tint = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(MediumSpace))
        Text(
            text = user.description,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(MediumSpace)
        )
        Spacer(modifier = Modifier.height(MediumSpace))
        ProfileStats(
            user ,
            isOwnProfile = isOwnProfile
        )
    }
}