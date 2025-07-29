package pw.coding.konnecto.feature_post.util

import pw.coding.konnecto.core.util.Error

sealed class PostDescriptionError: Error() {
    object FieldEmpty: PostDescriptionError()
}