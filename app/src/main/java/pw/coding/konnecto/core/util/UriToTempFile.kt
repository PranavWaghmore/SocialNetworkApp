package pw.coding.konnecto.core.util

import android.content.Context
import android.net.Uri
import java.io.File

fun uriToTempFile(context: Context, uri: Uri): File {
    // If it is already a file:// uri, you can return it directly
    if (uri.scheme == "file") return File(requireNotNull(uri.path))

    // Otherwise copy content:// into cache file
    val input = context.contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("Cannot open uri: $uri")

    val file = File.createTempFile("upload_", ".jpg", context.cacheDir)
    file.outputStream().use { out -> input.use { it.copyTo(out) } }
    return file
}