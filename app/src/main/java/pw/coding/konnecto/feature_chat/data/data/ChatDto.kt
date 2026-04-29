package pw.coding.konnecto.feature_chat.data.data

import pw.coding.konnecto.feature_chat.domain.models.Chat

data class ChatDto(
    val chatId: String,
    val remoteUserId: String?,
    val remoteUsername: String?,
    val remoteUserProfilePictureUrl: String?,
    val lastMessage: String?,
    val timestamp: Long?
) {
    fun toChat(): Chat {
        return Chat(
            chatId = chatId,
            remoteUserId = remoteUserId ?: "unknownRemoteUserId",
            remoteUsername = remoteUsername ?: "Unknown user",
            remoteUserProfilePictureUrl = remoteUserProfilePictureUrl ?: "",
            lastMessage = lastMessage ?: "HardCoded",
            timestamp = timestamp ?: System.currentTimeMillis()
        )
    }
}
