package pw.coding.konnecto.feature_post.presentation.post_detail

import pw.coding.konnecto.core.domain.models.Comment
import pw.coding.konnecto.core.domain.models.Post

data class PostDetailState(
    val post: Post? = null,
    val comments: List<Comment> = emptyList(),
    val isLoadingPost: Boolean = false,
    val isLoadingComments: Boolean = false
)
