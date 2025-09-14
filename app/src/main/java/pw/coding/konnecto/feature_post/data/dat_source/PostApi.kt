package pw.coding.konnecto.feature_post.data.dat_source

import io.ktor.http.content.PartData
import okhttp3.MultipartBody
import pw.coding.konnecto.feature_post.data.dat_source.request.CreatePostRequest
import pw.coding.konnecto.core.data.dto.BasicApiResponse
import pw.coding.konnecto.core.domain.models.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PostApi {

    @GET("/api/post/get")
    suspend fun getPostsForFollows(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): List<Post>

    @Multipart
    @POST("/api/post/create")
    suspend fun createPost(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part
    ): BasicApiResponse<Unit>

    companion object{
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}