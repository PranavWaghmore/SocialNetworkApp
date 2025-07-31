package pw.coding.konnecto.feature_auth.domain.repository

import pw.coding.konnecto.core.util.Resource

interface AuthRepository {

    suspend fun register(
        email: String,
        username: String,
        password: String
    ): Resource<Unit>
}