package pw.coding.konnecto.feature_chat.domain.use_case

data class ChatUseCases(
    val sendMessage: SendMessage,
    val observeChatEvents: ObserveChatEvents,
    val observeMessages: ObserveMessages,
    val getChatsForUser: GetChatsForUserUseCase,
    val getMessagesForChat: GetMessagesForChat
)
