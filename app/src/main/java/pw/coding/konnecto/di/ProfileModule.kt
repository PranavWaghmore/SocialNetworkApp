package pw.coding.konnecto.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pw.coding.konnecto.core.data.remote.PostApi
import pw.coding.konnecto.feature_profile.data.remote.ProfileApi
import pw.coding.konnecto.core.data.repository.ProfileRepositoryImpl
import pw.coding.konnecto.core.domain.repository.ProfileRepository
import pw.coding.konnecto.feature_profile.domain.use_case.GetPostsForProfileUseCase
import pw.coding.konnecto.feature_profile.domain.use_case.GetProfileUseCase
import pw.coding.konnecto.feature_profile.domain.use_case.GetSkillsUseCase
import pw.coding.konnecto.feature_profile.domain.use_case.ProfileUseCases
import pw.coding.konnecto.feature_profile.domain.use_case.SearchForUsersUseCase
import pw.coding.konnecto.feature_profile.domain.use_case.SetSkillSelectedUseCase
import pw.coding.konnecto.core.domain.use_case.ToggleFollowStateForUserUSeCase
import pw.coding.konnecto.feature_profile.domain.use_case.UpdateProfileUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ProfileApi.BASE_URL)
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        profileApi: ProfileApi,
        postApi: PostApi,
        gson: Gson,
        @ApplicationContext context: Context
    ): ProfileRepository {
        return ProfileRepositoryImpl(profileApi,postApi,gson,context ,)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases{
        return ProfileUseCases(
            getProfile = GetProfileUseCase(repository),
            getSkills = GetSkillsUseCase(repository),
            updateProfile = UpdateProfileUseCase(repository),
            setSkillSelected = SetSkillSelectedUseCase(),
            getPostsForProfile = GetPostsForProfileUseCase(repository),
            searchUsers = SearchForUsersUseCase(repository),
            toggleFollowStateForUser = ToggleFollowStateForUserUSeCase(repository)
        )
    }
}