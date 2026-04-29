package pw.coding.konnecto.feature_profile.domain.use_case

import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_profile.domain.model.Skill
import pw.coding.konnecto.core.domain.repository.ProfileRepository

class GetSkillsUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): Resource<List<Skill>>{
        return repository.getSkills()
    }
}