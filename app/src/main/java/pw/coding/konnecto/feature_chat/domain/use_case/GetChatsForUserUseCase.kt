package pw.coding.konnecto.feature_chat.domain.use_case

import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_chat.domain.models.Chat
import pw.coding.konnecto.feature_chat.domain.repository.ChatRepository

class GetChatsForUserUseCase(
    private val repository: ChatRepository
) {

    suspend operator fun invoke() : Resource<List<Chat>>{
        return repository.getChatsForUser()
    }
}