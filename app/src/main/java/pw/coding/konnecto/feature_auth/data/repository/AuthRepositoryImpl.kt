package pw.coding.konnecto.feature_auth.data.repository

import pw.coding.konnecto.R
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_auth.data.dto.request.CreateAccountRequest
import pw.coding.konnecto.feature_auth.data.remote.AuthApi
import pw.coding.konnecto.feature_auth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi
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
                 Resource.Error(UiText.DynamicString("Network error: ${e.localizedMessage}"))
         }catch (e: HttpException){
             Resource.Error( UiText.StringResource(R.string.something_went_wrong))
        }
    }
}