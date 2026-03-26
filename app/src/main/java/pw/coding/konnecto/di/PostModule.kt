package pw.coding.konnecto.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pw.coding.konnecto.core.data.remote.PostApi
import pw.coding.konnecto.feature_post.data.repository.PostRepositoryImpl
import pw.coding.konnecto.feature_post.domain.repository.PostRepository
import pw.coding.konnecto.feature_post.domain.use_case.AddCommentForPostUseCase
import pw.coding.konnecto.feature_post.domain.use_case.CreatePostUseCase
import pw.coding.konnecto.feature_post.domain.use_case.GetCommentsForPostUseCase
import pw.coding.konnecto.feature_post.domain.use_case.GetPostDetailsUseCase
import pw.coding.konnecto.feature_post.domain.use_case.GetPostForFollowsUseCase
import pw.coding.konnecto.feature_post.domain.use_case.GetUsersWhoLikedParentUseCase
import pw.coding.konnecto.feature_post.domain.use_case.PostUseCases
import pw.coding.konnecto.feature_post.domain.use_case.ToggleLikeForParentUseCase
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
    fun providePostRepository(
        api: PostApi,
        gson: Gson
    ): PostRepository{
        return PostRepositoryImpl(api, gson)
    }
    @Provides
    @Singleton
    fun providePostUseCase(repository: PostRepository): PostUseCases{
        return PostUseCases(
            getPostForFollows =  GetPostForFollowsUseCase(repository),
            createPostUseCase = CreatePostUseCase(repository),
            getPostDetails = GetPostDetailsUseCase(repository),
            getComments = GetCommentsForPostUseCase(repository),
            addComment = AddCommentForPostUseCase(repository),
            toggleLikeForParent = ToggleLikeForParentUseCase(repository),
            getUsersWhoLikedParent = GetUsersWhoLikedParentUseCase(repository)
       )
    }
}