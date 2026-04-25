package pw.coding.konnecto.feature_auth.data.remote

import pw.coding.konnecto.core.data.dto.response.BasicApiResponse
import pw.coding.konnecto.feature_auth.data.remote.request.CreateAccountRequest
import pw.coding.konnecto.feature_auth.data.remote.request.LoginRequest
import pw.coding.konnecto.feature_auth.data.remote.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body createAccountRequest: CreateAccountRequest
    ): BasicApiResponse<Unit>

    @POST("/api/user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): BasicApiResponse<AuthResponse>

    @GET("/api/user/authenticate")
    suspend fun authenticate()

    companion object{
        const val BASE_URL = "http://192.168.1.38:8001/"
    }
}