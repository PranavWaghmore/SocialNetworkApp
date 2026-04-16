package pw.coding.konnecto.feature_chat.domain.repository

import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_chat.domain.models.Chat
import pw.coding.konnecto.feature_chat.domain.models.Message

interface ChatRepository {

    fun observeEvents(): Flow<WebSocket.Event>

    fun sendMessages(toId: String, text: String, chatId: String?)

    fun observeMessages(): Flow<Message>

    suspend fun getChatsForUser(): Resource<List<Chat>>

    suspend fun getMessagesForChat(chatId: String, page: Int,  pageSize: Int): Resource<List<Message>>
}