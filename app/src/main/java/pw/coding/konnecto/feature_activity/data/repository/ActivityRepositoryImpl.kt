package pw.coding.konnecto.feature_activity.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.domain.models.Activity
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.feature_activity.data.paging.ActivitySource
import pw.coding.konnecto.feature_activity.data.remote.ActivityApi
import pw.coding.konnecto.feature_activity.domain.repository.ActivityRepository

class ActivityRepositoryImpl(
    private val api: ActivityApi
) : ActivityRepository {
    override val activities: Flow<PagingData<Activity>>
        get() = Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)){
            ActivitySource(api)
        }.flow
}