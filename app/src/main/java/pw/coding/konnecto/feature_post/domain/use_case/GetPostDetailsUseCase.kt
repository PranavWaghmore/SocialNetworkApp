package pw.coding.konnecto.feature_post.domain.use_case

import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_post.domain.repository.PostRepository

class GetPostDetailsUseCase(
    private val repository: PostRepository
){

    suspend operator fun invoke(
        postId: String
    ): Resource<Post>{
        return repository.getPost(postId)
    }
}