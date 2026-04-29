package pw.coding.konnecto.core.util

import android.content.Context
import android.content.Intent
import pw.coding.konnecto.R

fun  Context.sendSharePost(postId: String){

    val shareLink = "konnecto://post/$postId"

    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            getString(
                R.string.share_intent_text,
                shareLink
            )
        )
        type = "text/plain"
    }

    if(intent.resolveActivity(packageManager) != null){
        startActivity(Intent.createChooser(intent,"Select an app"))
    }
}