package pw.coding.konnecto.feature_post.data.request

data class LikeUpdateRequest(
    val parentId: String,
    val parentType: Int
)
