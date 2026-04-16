package pw.coding.konnecto.feature_chat.presentation.message

sealed class MessageEvent {
    data class EnteredMessage(val message: String): MessageEvent()
    object SendMessage: MessageEvent()
}