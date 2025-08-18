package pw.coding.konnecto.feature_post.domain.repository

import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource

interface PostRepository {

    suspend fun getPostsForFollows(
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): Resource<List<Post>>
}