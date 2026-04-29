package pw.coding.konnecto.feature_chat.domain.use_case

import pw.coding.konnecto.feature_chat.domain.repository.ChatRepository

class SendMessage(
    private val repository: ChatRepository
) {

    operator fun invoke(toId: String, text: String, chatId: String?){

        if(text.isBlank()){
            return
        }
        repository.sendMessages(toId,text,chatId)
    }
}