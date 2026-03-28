package pw.coding.konnecto.feature_profile.domain.use_case

import pw.coding.konnecto.core.domain.repository.ProfileRepository

class LogoutUseCase(
    private val repository: ProfileRepository
) {

    operator fun invoke(){
        repository.logout()
    }
}