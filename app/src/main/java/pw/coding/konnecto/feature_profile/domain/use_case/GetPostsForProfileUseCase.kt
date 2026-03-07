package pw.coding.konnecto.feature_profile.domain.use_case

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.feature_profile.domain.repository.ProfileRepository

class GetPostsForProfileUseCase(
    private val repository: ProfileRepository
) {

    operator fun invoke(
        userId: String
    ): Flow<PagingData<Post>>{
        return repository.getPostForProfile(userId)
    }
}