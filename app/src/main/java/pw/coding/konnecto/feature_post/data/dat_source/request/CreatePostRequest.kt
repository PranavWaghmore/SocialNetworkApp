package pw.coding.konnecto.feature_post.data.dat_source.request

import android.net.Uri

data class CreatePostRequest(
    val description: String,
    val imageUri: Uri
)
