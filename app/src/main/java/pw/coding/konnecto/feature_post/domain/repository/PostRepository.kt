package pw.coding.konnecto.feature_post.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.domain.models.Comment
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import java.net.URI

interface PostRepository {

    val posts: Flow<PagingData<Post>>

    suspend fun createPost(
        description: String,
        imageUri: Uri
    ): SimpleResource

    suspend fun getPost(
        postId: String
    ): Resource<Post>

    suspend fun getCommentsForPost(
        postId: String
    ): Resource<List<Comment>>

    suspend fun addComment(
        comment: String,
        postId: String
    ): SimpleResource
}