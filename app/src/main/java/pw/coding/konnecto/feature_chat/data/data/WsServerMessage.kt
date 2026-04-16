package pw.coding.konnecto.feature_chat.data.data

import pw.coding.konnecto.core.presentation.util.DateFormatUtil
import pw.coding.konnecto.feature_chat.domain.models.Message

data class WsServerMessage(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val chatId: String?,
) {
    fun toMessage(): Message {
        return Message(
            fromId = fromId,
            toId = toId,
            text = text,
            timestamp = timestamp,
            chatId = chatId,
        )
    }
}
