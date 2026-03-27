package pw.coding.konnecto.feature_activity.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.domain.models.Activity
import pw.coding.konnecto.core.util.Resource

interface ActivityRepository {

    suspend fun getActivities(
        page: Int,
        pageSize: Int
    ): Resource<List<Activity>>
}