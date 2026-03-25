package pw.coding.konnecto.feature_profile.domain.use_case

import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.domain.repository.ProfileRepository
import pw.coding.konnecto.core.util.Resource

class GetPostsForProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        userId: String,
        page: Int,
    ): Resource<List<Post>>{

        return repository.getPostForProfile(
            userId = userId,
            page = page
        )
    }
}