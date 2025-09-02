package pw.coding.konnecto.feature_post.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource

interface PostRepository {

    val posts: Flow<PagingData<Post>>
}