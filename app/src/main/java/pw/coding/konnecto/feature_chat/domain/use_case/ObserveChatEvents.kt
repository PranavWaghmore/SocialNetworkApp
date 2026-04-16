package pw.coding.konnecto.feature_chat.domain.use_case

import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.feature_chat.data.ChatService
import pw.coding.konnecto.feature_chat.domain.repository.ChatRepository

class ObserveChatEvents(
    private val repository: ChatRepository
) {

    operator fun invoke(): Flow<WebSocket.Event>{
        return repository.observeEvents()
    }
}