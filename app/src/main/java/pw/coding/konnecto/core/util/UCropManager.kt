package pw.coding.konnecto.core.util

import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.core.content.ContextCompat
import pw.coding.konnecto.R
import com.yalantis.ucrop.UCrop
import java.io.File

object UCropManager {

    fun startCrop(
        context: Context,
        sourceUri: Uri
    ): android.content.Intent {

        val destinationUri = Uri.fromFile(
            File(
                context.cacheDir,
                "cropped_${System.currentTimeMillis()}.jpg"
            )
        )

        val options = UCrop.Options().apply {

            // Compression quality
            setCompressionQuality(85)

            // Instagram-like square crop
            setFreeStyleCropEnabled(false)

            // Toolbar customization
            setToolbarColor(
                ContextCompat.getColor(context, R.color.black)
            )
            setStatusBarColor(
                ContextCompat.getColor(context, R.color.black)
            )
            setToolbarWidgetColor(Color.WHITE)

            // Controls
            setHideBottomControls(false)
            setShowCropFrame(true)
            setShowCropGrid(true)
        }

        return UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f) // Instagram style square
            .withMaxResultSize(1080, 1080)
            .withOptions(options)
            .getIntent(context)
    }
}