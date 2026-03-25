package pw.coding.konnecto.feature_profile.data.remote


import okhttp3.MultipartBody
import pw.coding.konnecto.feature_profile.data.response.ProfileResponse
import pw.coding.konnecto.core.data.dto.response.BasicApiResponse
import pw.coding.konnecto.core.data.dto.response.UserItemDto
import pw.coding.konnecto.feature_profile.data.request.FollowUpdateRequest
import pw.coding.konnecto.feature_profile.data.response.SkillDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileApi {

    @GET("/api/user/profile")
    suspend fun getProfile(
        @Query("userId") userId: String,
    ): BasicApiResponse<ProfileResponse>

    @GET("api/user/search")
    suspend fun searchUsers(
        @Query ("query") query: String
    ): List<UserItemDto>

    @POST("api/following/follow")
    suspend fun followUser(
        @Body request: FollowUpdateRequest
    ): BasicApiResponse<Unit>

    @DELETE("api/following/unfollow")
    suspend fun unFollowUser(
       @Query ("UserId") userId: String,
    ): BasicApiResponse<Unit>

    @PUT("/api/user/update")
    @Multipart
    suspend fun updateProfile(
        @Part updateProfileData: MultipartBody.Part,
        @Part profilePicture: MultipartBody.Part?,
        @Part bannerImage: MultipartBody.Part?
    ): BasicApiResponse<Unit>

    @GET("/api/skills/get")
    suspend fun getSkills(): List<SkillDto>

    companion object{
        const val BASE_URL = "http://192.168.1.36:8001/"
    }
}