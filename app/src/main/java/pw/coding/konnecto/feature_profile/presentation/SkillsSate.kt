package pw.coding.konnecto.feature_profile.presentation

import pw.coding.konnecto.feature_profile.domain.model.Skill

data class SkillsSate(
    val skills : List<Skill> = emptyList(),
    val selectedSkills: List<Skill> = emptyList()
)
