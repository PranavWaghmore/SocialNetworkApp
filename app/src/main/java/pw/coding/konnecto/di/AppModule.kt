package pw.coding.konnecto.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.http.ContentType
import pw.coding.konnecto.core.util.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences{
        return app.getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            MODE_PRIVATE
        )
    }
}