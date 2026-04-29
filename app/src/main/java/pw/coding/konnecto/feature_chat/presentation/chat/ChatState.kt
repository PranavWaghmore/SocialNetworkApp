package pw.coding.konnecto.feature_chat.presentation.chat

import pw.coding.konnecto.feature_chat.domain.models.Chat

data class ChatState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false
)
