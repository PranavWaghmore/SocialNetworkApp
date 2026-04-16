package pw.coding.konnecto.feature_chat.presentation.message

import io.ktor.http.ContentType
import pw.coding.konnecto.feature_chat.domain.models.Message

data class MessageState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
)
