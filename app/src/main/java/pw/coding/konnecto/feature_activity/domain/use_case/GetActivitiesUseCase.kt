package pw.coding.konnecto.feature_activity.domain.use_case

import pw.coding.konnecto.core.domain.models.Activity
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_activity.domain.repository.ActivityRepository

class GetActivitiesUseCase(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(
        page: Int,
        paSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): Resource<List<Activity>>{

        return repository.getActivities(
            page =  page,
            pageSize = paSize
        )
    }
}