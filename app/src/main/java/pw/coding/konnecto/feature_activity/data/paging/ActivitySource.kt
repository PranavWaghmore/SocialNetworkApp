package pw.coding.konnecto.feature_activity.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import kotlinx.io.IOException
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.domain.models.Activity
import pw.coding.konnecto.feature_activity.data.remote.ActivityApi

class ActivitySource(
    private val api: ActivityApi
) : PagingSource<Int, Activity>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Activity> {
        return try {
            val nextPage = params.key ?: 0
            val activities =  api.getActivities(
                page = nextPage,
                pageSize = Constants.DEFAULT_PAGE_SIZE
            )
            LoadResult.Page(
                data = activities.map { it.toActivity() },
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (activities.isEmpty()) null else nextPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Activity>): Int? {
        return state.anchorPosition
    }

}