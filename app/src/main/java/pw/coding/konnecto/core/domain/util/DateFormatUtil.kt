package pw.coding.konnecto.core.domain.util

import java.util.*
import java.text.SimpleDateFormat

object DateFormatUtil {
    fun timestampToFormattedString(timestamp: Long, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).run {
            format(timestamp)
        }
    }
}