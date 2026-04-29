package pw.coding.konnecto.feature_profile.data.response

import pw.coding.konnecto.feature_profile.domain.model.Skill

data class SkillDto (
    val name: String,
    val imageUrl: String
){
    fun toSkill(): Skill{
        return Skill(
            name = name,
            imageUrl = imageUrl
        )
    }
}