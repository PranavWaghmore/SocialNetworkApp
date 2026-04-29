package pw.coding.konnecto.feature_chat.data.data

data class WsClientMessage(
    val toId: String,
    val text: String,
    val chatId: String?,
)
