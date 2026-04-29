package pw.coding.konnecto.feature_post.domain.use_case

import pw.coding.konnecto.core.domain.models.UserItem
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_post.domain.repository.PostRepository

class GetUsersWhoLikedParentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        parentId: String
    ): Resource<List<UserItem>>{

        return repository.getUsersWhoLikedParent(
            parentId
        )
    }
}