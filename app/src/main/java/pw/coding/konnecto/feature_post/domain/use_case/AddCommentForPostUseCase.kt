package pw.coding.konnecto.feature_post.domain.use_case

import pw.coding.konnecto.R
import pw.coding.konnecto.core.data.remote.PostApi
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_post.domain.repository.PostRepository

class AddCommentForPostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        comment: String,
        postId: String
    ): SimpleResource{

        if(comment.isBlank()){
            return Resource.Error(uiText = UiText.StringResource(R.string.this_field_cant_be_empty))
        }
        return repository.addComment(
            comment = comment,
            postId = postId
        )
    }
}