package pw.coding.konnecto.feature_activity.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.domain.models.Activity

interface ActivityRepository {

    val activities : Flow<PagingData<Activity>>
}