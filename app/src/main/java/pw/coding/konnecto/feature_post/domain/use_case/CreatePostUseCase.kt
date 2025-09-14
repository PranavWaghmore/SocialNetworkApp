package pw.coding.konnecto.feature_post.domain.use_case

import android.net.Uri
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.feature_post.domain.repository.PostRepository

class CreatePostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        description: String,
        imageUri: Uri
    ): SimpleResource{
       return  repository.createPost(description,imageUri)
    }
}