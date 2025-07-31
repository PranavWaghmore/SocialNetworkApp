package pw.coding.konnecto.feature_auth.data.remote

import pw.coding.konnecto.core.data.dto.BasicApiResponse
import pw.coding.konnecto.feature_auth.data.dto.request.CreateAccountRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body createAccountRequest: CreateAccountRequest
    ): BasicApiResponse

    companion object{
        const val BASE_URL = "http://10.0.2.2: 8001/"
    }
}