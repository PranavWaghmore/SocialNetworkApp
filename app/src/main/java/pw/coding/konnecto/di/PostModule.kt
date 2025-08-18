package pw.coding.konnecto.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pw.coding.konnecto.feature_post.data.dat_source.PostApi
import pw.coding.konnecto.feature_post.data.repository.PostRepositoryImpl
import pw.coding.konnecto.feature_post.domain.repository.PostRepository
import pw.coding.konnecto.feature_post.domain.use_case.GetPostForFollowsUseCase
import pw.coding.konnecto.feature_post.domain.use_case.PostUseCases
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PostModule {
    @Provides
    @Singleton
    fun providePostApi(client: OkHttpClient): PostApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostApi.BASE_URL)
            .client(client)
            .build()
            .create(PostApi::class.java)
    }
    @Provides
    @Singleton
    fun providePostRepository(api: PostApi): PostRepository{
        return PostRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun providePostUseCase(repository: PostRepository): PostUseCases{
        return PostUseCases(GetPostForFollowsUseCase(repository))
    }
}