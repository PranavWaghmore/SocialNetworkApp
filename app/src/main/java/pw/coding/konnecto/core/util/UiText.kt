package pw.coding.konnecto.core.util

import androidx.annotation.StringRes
import pw.coding.konnecto.R

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    data class StringResource(@StringRes val id: Int): UiText()

    companion object{
        fun unknownError(): UiText {
            return UiText.StringResource(R.string.unknown_error)
        }
    }
}