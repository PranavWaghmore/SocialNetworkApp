package pw.coding.konnecto.feature_activity.data.repository

import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.Activity
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_activity.data.remote.ActivityApi
import pw.coding.konnecto.feature_activity.domain.repository.ActivityRepository
import retrofit2.HttpException
import java.io.IOException

class ActivityRepositoryImpl(
    private val api: ActivityApi
) : ActivityRepository {

    override suspend fun getActivities(
        page: Int,
        pageSize: Int
    ): Resource<List<Activity>> {
        return try {
            val response = api.getActivities(
                page = page,
                pageSize =  pageSize
            )
            Resource.Success(data = response.map { it.toActivity() })
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