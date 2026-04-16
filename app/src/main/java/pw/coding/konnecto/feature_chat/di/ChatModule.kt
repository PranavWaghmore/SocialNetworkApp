package pw.coding.konnecto.feature_chat.di

import com.google.gson.Gson
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.feature_chat.data.ChatService
import pw.coding.konnecto.feature_chat.data.remote.ChatApi
import pw.coding.konnecto.feature_chat.data.remote.util.CustomGsonMessageAdapter
import pw.coding.konnecto.feature_chat.data.repository.ChatRepositoryImpl
import pw.coding.konnecto.feature_chat.domain.repository.ChatRepository
import pw.coding.konnecto.feature_chat.domain.use_case.ChatUseCases
import pw.coding.konnecto.feature_chat.domain.use_case.GetChatsForUserUseCase
import pw.coding.konnecto.feature_chat.domain.use_case.GetMessagesForChat
import pw.coding.konnecto.feature_chat.domain.use_case.ObserveChatEvents
import pw.coding.konnecto.feature_chat.domain.use_case.ObserveMessages
import pw.coding.konnecto.feature_chat.domain.use_case.SendMessage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideScarlet(client: OkHttpClient): Scarlet {
        return Scarlet.Builder()
            .addMessageAdapterFactory(CustomGsonMessageAdapter.Factory(Gson()))
            .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
            .webSocketFactory(
                client.newWebSocketFactory("ws://192.168.1.41:8001/api/chat/websocket")
            )
            .backoffStrategy(LinearBackoffStrategy(Constants.RECONNECT_INTERVAL))
            .build()
    }

    @Provides
    @Singleton
    fun provideChatService(scarlet: Scarlet): ChatService {
        return scarlet.create(ChatService::class.java)
    }

    @Provides
    @Singleton
    fun provideChatApi(client: OkHttpClient): ChatApi {
        return Retrofit.Builder()
            .baseUrl(ChatApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        api: ChatApi,
        chatService: ChatService
    ): ChatRepository {
        return ChatRepositoryImpl(api, chatService)
    }

    @Provides
    @Singleton
    fun provideChatUseCases(
        repository: ChatRepository
    ): ChatUseCases {
        return ChatUseCases(
            sendMessage = SendMessage(repository),
            observeChatEvents = ObserveChatEvents(repository),
            observeMessages = ObserveMessages(repository),
            getChatsForUser = GetChatsForUserUseCase(repository),
            getMessagesForChat = GetMessagesForChat(repository)
        )
    }
}