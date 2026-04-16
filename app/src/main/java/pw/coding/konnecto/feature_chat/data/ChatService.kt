package pw.coding.konnecto.feature_chat.data

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel
import pw.coding.konnecto.feature_chat.data.data.WsClientMessage
import pw.coding.konnecto.feature_chat.data.data.WsServerMessage

interface ChatService {

    @Receive
    fun observeEvents(): ReceiveChannel<WebSocket.Event>

    @Send
    fun sendMessages(message: WsClientMessage)

    @Receive
    fun observeMessages(): ReceiveChannel<WsServerMessage>
}