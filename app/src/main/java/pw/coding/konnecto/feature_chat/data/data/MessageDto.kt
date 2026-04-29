package pw.coding.konnecto.feature_chat.data.data

import pw.coding.konnecto.feature_chat.domain.models.Message

data class MessageDto(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val chatId: String?,
    val id: String,
){
    fun toMessage(): Message{
        return Message(
            fromId = fromId,
            toId = toId,
            text = text,
            timestamp = timestamp,
            chatId = chatId
        )
    }
}
