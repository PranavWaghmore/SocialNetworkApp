package pw.coding.konnecto.feature_auth.domain.use_case

import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.feature_auth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): SimpleResource {
        return repository.authenticate()
    }
}