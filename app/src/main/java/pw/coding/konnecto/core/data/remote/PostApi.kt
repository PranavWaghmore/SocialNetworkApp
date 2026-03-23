package pw.coding.konnecto.core.data.remote

import android.R
import okhttp3.MultipartBody
import okhttp3.Request
import pw.coding.data.requests.CreateCommentRequest
import pw.coding.konnecto.core.data.dto.BasicApiResponse
import pw.coding.konnecto.core.domain.models.Comment
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.feature_post.data.remote.dto.CommentDto
import pw.coding.konnecto.feature_post.data.request.LikeUpdateRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PostApi {

    @GET("/api/post/get")
    suspend fun getPostsForFollows(
        @Query("page") page : Int,
        @Query("pageSize") pageSize: Int,
    ): List<Post>

    @GET("api/user/post")
    suspend fun getPostsForProfile(
        @Query("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): List<Post>

    @Multipart
    @POST("/api/post/create")
    suspend fun createPost(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part
    ): BasicApiResponse<Unit>

    @GET("api/post/details")
    suspend fun getPost(
        @Query("postId") postId: String
    ): BasicApiResponse<Post>

    @GET("api/comment/get")
    suspend fun getCommentsForPost(
        @Query("postId") postId: String
    ): List<CommentDto>

    @POST("/api/comment/create")
    suspend fun addComment(
        @Body request: CreateCommentRequest
    ): BasicApiResponse<Unit>

    @POST("/api/like")
    suspend fun likeParent(
        @Body request: LikeUpdateRequest
    ): BasicApiResponse<Unit>

    @DELETE("/api/unlike")
    suspend fun unlikeParent(
        @Query ("parentId") parentId: String,
        @Query("parentType") parentType: Int
    ): BasicApiResponse<Unit>

    companion object{
        const val BASE_URL = "http://192.168.1.37:8001/"
    }
}