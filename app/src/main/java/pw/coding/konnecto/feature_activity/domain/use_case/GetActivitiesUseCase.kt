package pw.coding.konnecto.feature_activity.domain.use_case

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.domain.models.Activity
import pw.coding.konnecto.feature_activity.domain.repository.ActivityRepository

class GetActivitiesUseCase(
    private val repository: ActivityRepository
) {
    operator fun invoke(): Flow<PagingData<Activity>>{
        return repository.activities
    }
}