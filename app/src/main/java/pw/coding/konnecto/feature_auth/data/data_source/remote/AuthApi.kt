package pw.coding.konnecto.feature_auth.data.data_source.remote

import pw.coding.konnecto.core.data.dto.BasicApiResponse
import pw.coding.konnecto.feature_auth.data.dto.request.CreateAccountRequest
import pw.coding.konnecto.feature_auth.data.dto.request.LoginRequest
import pw.coding.konnecto.feature_auth.data.dto.response.AuthResponse
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
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}