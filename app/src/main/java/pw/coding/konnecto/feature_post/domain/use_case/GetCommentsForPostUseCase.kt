package pw.coding.konnecto.feature_post.domain.use_case

import pw.coding.konnecto.core.domain.models.Comment
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_post.domain.repository.PostRepository

class GetCommentsForPostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        postId: String
    ): Resource<List<Comment>>{
        return repository.getCommentsForPost(postId)
    }
}