package pw.coding.konnecto.core.domain.use_case

import android.content.SharedPreferences
import pw.coding.konnecto.core.util.Constants

class GetOwnUserIdUseCase(
    private val sharedPreferences: SharedPreferences
) {

    operator fun invoke(): String{
        return sharedPreferences.getString(Constants.KEY_USER_ID,"") ?: ""
    }
}