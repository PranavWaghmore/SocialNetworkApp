package pw.coding.konnecto.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pw.coding.konnecto.feature_auth.data.remote.AuthApi
import pw.coding.konnecto.feature_auth.data.repository.AuthRepositoryImpl
import pw.coding.konnecto.feature_auth.domain.repository.AuthRepository
import pw.coding.konnecto.feature_auth.domain.use_case.LoginUseCase
import pw.coding.konnecto.feature_auth.domain.use_case.RegisterUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi{
        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi , sharedPreferences: SharedPreferences): AuthRepository{
        return AuthRepositoryImpl(api,sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRegisterUSeCase(repository: AuthRepository): RegisterUseCase{
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUSeCase(repository: AuthRepository): LoginUseCase{
        return LoginUseCase(repository)
    }
}