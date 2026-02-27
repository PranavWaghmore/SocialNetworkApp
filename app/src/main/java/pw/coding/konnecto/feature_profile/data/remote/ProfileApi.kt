package pw.coding.konnecto.feature_profile.data.remote


import pw.coding.konnecto.feature_profile.data.response.ProfileResponse
import pw.coding.konnecto.core.data.dto.BasicApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {

    @GET("/api/user/profile")
    suspend fun getProfile(
        @Query("userId") userId: String,
    ): BasicApiResponse<ProfileResponse>

    companion object{
        const val BASE_URL = "http://192.168.1.39:8001/"
    }
}