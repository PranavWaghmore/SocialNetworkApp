package pw.coding.konnecto.feature_chat.domain.models

import java.sql.Timestamp

data class Message (
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val chatId: String?,
)