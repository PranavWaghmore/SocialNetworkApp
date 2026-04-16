package pw.coding.konnecto.feature_chat.presentation.chat

import android.util.Base64
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace
import pw.coding.konnecto.core.presentation.ui.theme.SmallSpace
import pw.coding.konnecto.core.util.Screen
import pw.coding.konnecto.feature_chat.domain.models.Chat

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {
    val chats = viewModel.state.value.chats
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(SmallSpace)
    ) {
        // Top toolbar of the screen
        StandardToolBar(
            title = {
                Text(
                    text = "Chats",
                    color = Color.White
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false
        )

        // Chat list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(LargeSpace),
            verticalArrangement = Arrangement.spacedBy(SmallSpace)
        ) {
            items(chats) { chat ->
                ChatItem(
                    chat = chat,
                    onChatClick = {
                        onNavigate(
                            Screen.MessagesScreen.route +
                                    "/${chat.remoteUserId}/${chat.remoteUsername}/" +
                                    "${Base64.encodeToString(chat.remoteUserProfilePictureUrl.encodeToByteArray(), 0)}" +
                                    "?chatId=${chat.chatId}"
                        )
                    }
                )

                Spacer(modifier = Modifier.height(LargeSpace))
            }
        }
    }
}