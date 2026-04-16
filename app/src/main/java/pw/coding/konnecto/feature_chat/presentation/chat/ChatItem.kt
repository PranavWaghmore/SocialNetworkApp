package pw.coding.konnecto.feature_chat.presentation.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pw.coding.konnecto.R
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.presentation.ui.theme.ProfilePictureDpSize
import pw.coding.konnecto.core.presentation.ui.theme.SmallSpace
import pw.coding.konnecto.core.presentation.util.DateFormatUtil
import pw.coding.konnecto.feature_chat.domain.models.Chat

@Composable
fun ChatItem(
    chat: Chat,
    modifier: Modifier = Modifier,
    onChatClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onChatClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = SmallSpace,
                    horizontal = MediumSpace
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile picture of the other user
            AsyncImage(
                model = chat.remoteUserProfilePictureUrl,
                contentDescription = stringResource(R.string.profile_picture),
                modifier = Modifier
                    .size(ProfilePictureDpSize)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(SmallSpace))

            // Text content takes the remaining horizontal space
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.remoteUsername,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Spacer(modifier = Modifier.width(SmallSpace))

                    Text(
                        text = DateFormatUtil.timestampToFormattedString(chat.timestamp,"HH:mm:ss"),
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1
                    )
                }

                Text(
                    text = chat.lastMessage,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}