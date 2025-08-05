package pw.coding.konnecto.feature_auth.data.repository

import android.content.SharedPreferences
import pw.coding.konnecto.R
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_auth.data.dto.request.CreateAccountRequest
import pw.coding.konnecto.feature_auth.data.dto.request.LoginRequest
import pw.coding.konnecto.feature_auth.data.remote.AuthApi
import pw.coding.konnecto.feature_auth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
): AuthRepository {
    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource{
        val request = CreateAccountRequest(email, username, password)
         return try {
            val response = api.register(request)
             if(response.successful){
                 Resource.Success(Unit)
             }else{
                 response.message?.let { msg ->
                     Resource.Error(UiText.DynamicString(msg))
                 } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
             }
        }catch (e : IOException){
                 Resource.Error(UiText.StringResource(R.string.check_your_internet_connection))
         }catch (e: HttpException){
             Resource.Error( UiText.StringResource(R.string.something_went_wrong))
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): SimpleResource {
        val request = LoginRequest(email,password)
        return try {
            val response = api.login(request)
            if(response.successful){
                response.data?.token?.let { token ->
                    sharedPreferences.edit()
                        .putString(Constants.KEY_JWT_TOKEN , token)
                        .apply()
                }
                Resource.Success(Unit)
            }else{
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        }catch (e : IOException){
            Resource.Error(UiText.StringResource(R.string.check_your_internet_connection))
        }catch (e: HttpException){
            Resource.Error( UiText.StringResource(R.string.something_went_wrong))
        }
    }
}