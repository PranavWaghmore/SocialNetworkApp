package pw.coding.konnecto.feature_chat.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pw.coding.konnecto.feature_chat.data.ChatService
import pw.coding.konnecto.feature_chat.domain.models.Message
import pw.coding.konnecto.feature_chat.domain.repository.ChatRepository

class ObserveMessages(
    private val repository: ChatRepository
) {

    operator fun invoke(): Flow<Message>{
        return repository.observeMessages()
    }
}