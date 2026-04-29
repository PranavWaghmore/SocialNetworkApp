package pw.coding.konnecto.feature_post.data.repository

import android.net.Uri
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import pw.coding.data.requests.CreateCommentRequest
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.core.data.remote.PostApi
import pw.coding.konnecto.feature_post.data.request.CreatePostRequest
import pw.coding.konnecto.core.data.paging.PostSource
import pw.coding.konnecto.core.domain.models.Comment
import pw.coding.konnecto.core.domain.models.UserItem
import pw.coding.konnecto.feature_post.data.request.LikeUpdateRequest
import pw.coding.konnecto.feature_post.domain.repository.PostRepository
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val api: PostApi,
    private val gson: Gson
) : PostRepository {
    override suspend fun getPostsForFollows(
        page: Int,
        pageSize: Int
    ): Resource<List<Post>> {
        return try {
            val response = api.getPostsForFollows(
                page = page,
                pageSize = pageSize
            )
            Resource.Success(data = response)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }


    override suspend fun createPost(description: String, imageUri: Uri): SimpleResource {
        val request = CreatePostRequest(description, imageUri)
        val file = imageUri.toFile()
        return try {
            val response = api.createPost(
                postData = MultipartBody.Part.createFormData(
                    name = "post_data",
                    value = gson.toJson(request)
                ),
                postImage = MultipartBody.Part.createFormData(
                    name = "post_image",
                    filename = file.name,
                    body = file.asRequestBody()
                )
            )
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: java.io.IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun getPost(postId: String): Resource<Post> {
        return try {
            val response = api.getPost(postId)
            if (response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun getCommentsForPost(postId: String): Resource<List<Comment>> {
        return try {
            val comments = api.getCommentsForPost(postId).map {
                it.toComment()
            }
            Resource.Success(comments)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun addComment(
        comment: String,
        postId: String
    ): SimpleResource {
        return try {
            val response = api.addComment(
                request = CreateCommentRequest(
                    comment = comment,
                    postId = postId
                )
            )
            if (response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun likeParent(
        parentId: String,
        parentType: Int
    ): SimpleResource {
        return try {
            val response = api.likeParent(
                request = LikeUpdateRequest(
                    parentId = parentId,
                    parentType = parentType
                )
            )
            if (response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun unlikeParent(parentId: String, parentType: Int): SimpleResource {
        return try {
            val response = api.unlikeParent(
                parentId = parentId,
                parentType = parentType
            )
            if (response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun getUsersWhoLikedParent(parentId: String): Resource<List<UserItem>> {
        return try {
             val response = api.getUsersWhoLikedParent(
                parentId = parentId
            )
            Resource.Success(response.map { it.toUserItem() })
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }


}

