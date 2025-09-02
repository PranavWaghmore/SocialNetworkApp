package pw.coding.konnecto.feature_post.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.io.IOException
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_post.data.dat_source.PostApi
import pw.coding.konnecto.feature_post.data.paging.PostSource
import pw.coding.konnecto.feature_post.domain.repository.PostRepository
import retrofit2.HttpException

class PostRepositoryImpl(
    private val api: PostApi,
) : PostRepository {
    override val posts: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)){
            PostSource(api)
        }.flow
}

