package pw.coding.konnecto.feature_chat.domain.use_case

import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_chat.domain.models.Message
import pw.coding.konnecto.feature_chat.domain.repository.ChatRepository

class GetMessagesForChat(
    private val repository: ChatRepository
) {

    suspend operator fun invoke(
        chatId: String,
        page: Int,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): Resource<List<Message>>{

        return repository.getMessagesForChat(chatId,page,pageSize)
    }
}