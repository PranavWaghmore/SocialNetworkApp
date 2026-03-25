package pw.coding.konnecto.feature_profile.domain.use_case

import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_profile.domain.model.Profile
import pw.coding.konnecto.core.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
){
    suspend operator fun invoke(userId: String): Resource<Profile> {
        val result = repository.getProfile(userId)
        return result
    }
}
