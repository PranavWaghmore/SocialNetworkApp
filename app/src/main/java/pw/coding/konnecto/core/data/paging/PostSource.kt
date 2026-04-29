package pw.coding.konnecto.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import kotlinx.io.IOException
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.data.remote.PostApi

class PostSource(
    private val api: PostApi,
    private val source: Source
) : PagingSource<Int, Post>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val nextPage = params.key ?: 0
            val posts = when(source){
                is Source.Follows ->{
                    api.getPostsForFollows(
                        page = nextPage,
                        pageSize = Constants.DEFAULT_PAGE_SIZE
                    )
                }

                is Source.Profile -> {
                    api.getPostsForProfile(
                        userId = source.userId,
                        page = nextPage,
                        pageSize = Constants.DEFAULT_PAGE_SIZE
                    )
                }
            }
            LoadResult.Page(
                data = posts,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (posts.isEmpty()) null else nextPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    sealed class Source{
        object Follows: Source()
        data class Profile(val userId: String): Source()
    }
}