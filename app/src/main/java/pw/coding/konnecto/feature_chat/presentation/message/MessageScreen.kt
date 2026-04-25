package pw.coding.konnecto.feature_chat.presentation.message

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import okio.ByteString.Companion.decodeBase64
import pw.coding.konnecto.R
import pw.coding.konnecto.core.presentation.components.SendTextField
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.ui.theme.DarkerGreen
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.presentation.ui.theme.ProfilePictureDpSize
import java.nio.charset.Charset

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import pw.coding.konnecto.core.presentation.util.DateFormatUtil

@Composable
fun MessageScreen(
    remoteUserId: String,
    remoteUsername: String,
    encodedRemoteUserProfilePictureUrl: String,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: MessageViewModel = hiltViewModel()
) {
    val decodedRemoteUserProfilePictureUrl = remember {
        encodedRemoteUserProfilePictureUrl.decodeBase64()?.string(Charset.defaultCharset())
    }

    val pagingState = viewModel.pagingState.value


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StandardToolBar(
                onNavigateUp = onNavigateUp,
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = decodedRemoteUserProfilePictureUrl,
                            contentDescription = stringResource(R.string.profile_picture),
                            modifier = Modifier
                                .size(ProfilePictureDpSize)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(MediumSpace))
                        Text(
                            text = remoteUsername,
                            color = Color.White
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = true
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(MediumSpace)
            ) {
                items(pagingState.items.size) { i ->
                    val message = pagingState.items[i]

                    if (message.fromId == remoteUserId) {
                        RemoteMessage(
                            message = message.text,
                            formattedTime = DateFormatUtil.timestampToFormattedString(
                                message.timestamp,
                                "HH:mm"
                            ),
                            color = MaterialTheme.colorScheme.surface,
                            textColor = Color.White
                        )
                        Spacer(modifier = Modifier.height(MediumSpace))
                    } else {
                        OwnMessage(
                            message = message.text,
                            formattedTime = DateFormatUtil.timestampToFormattedString(
                                message.timestamp,
                                "HH:mm"
                            ),
                            color = DarkerGreen,
                            textColor = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.height(MediumSpace))
                    }
                }
            }

            SendTextField(
                state = viewModel.messageTextField.value,
                onValueChange = {
                    viewModel.onEvent(MessageEvent.EnteredMessage(it))
                },
                onSendClick = {
                    viewModel.onEvent(MessageEvent.SendMessage)
                }
            )
        }
    }
}