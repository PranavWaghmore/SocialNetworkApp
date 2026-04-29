package pw.coding.konnecto.feature_auth.domain.repository

import android.content.SharedPreferences
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource

interface AuthRepository {

    suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource

    suspend fun login(
        email: String,
        password: String
    ): SimpleResource

    suspend fun authenticate(): SimpleResource
}