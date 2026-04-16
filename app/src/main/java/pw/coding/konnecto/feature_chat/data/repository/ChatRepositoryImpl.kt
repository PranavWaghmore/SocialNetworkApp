package pw.coding.konnecto.feature_chat.data.repository

import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.consumeAsFlow
import pw.coding.konnecto.R
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_chat.data.ChatService
import pw.coding.konnecto.feature_chat.data.data.WsClientMessage
import pw.coding.konnecto.feature_chat.data.remote.ChatApi
import pw.coding.konnecto.feature_chat.domain.models.Chat
import pw.coding.konnecto.feature_chat.domain.models.Message
import pw.coding.konnecto.feature_chat.domain.repository.ChatRepository
import retrofit2.HttpException
import java.io.IOException

class ChatRepositoryImpl(
    private val api: ChatApi,
    private val chatService: ChatService
) : ChatRepository {

    override fun observeEvents(): Flow<WebSocket.Event> {
        return chatService.observeEvents().consumeAsFlow()
    }

    override fun sendMessages(toId: String, text: String, chatId: String?) {
        chatService.sendMessages(
            WsClientMessage(
                toId = toId,
                text = text,
                chatId = chatId
            )
        )
    }

    override fun observeMessages(): Flow<Message> {
        return chatService
            .observeMessages()
            .consumeAsFlow()
            .map { it.toMessage() }
    }

    override suspend fun getChatsForUser(): Resource<List<Chat>> {
        return try {
            val response = api.getChatsForUser()
            Resource.Success(
                data = response
                    .map { it.toChat() }

            )
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun getMessagesForChat(
        chatId: String,
        page: Int,
        pageSize: Int
    ): Resource<List<Message>> {
        return try {
            val messages = api.getMessagesForChat(chatId,page,pageSize)
            Resource.Success(data = messages.map { it.toMessage() })
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }


}