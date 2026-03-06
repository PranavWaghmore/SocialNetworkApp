package pw.coding.konnecto.feature_profile.domain.use_case

import pw.coding.konnecto.R
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_profile.domain.model.Skill
import pw.coding.konnecto.feature_profile.presentation.util.ProfileConstants

class SetSkillSelectedUseCase {

     operator fun invoke(
         selectedSkills: List<Skill>,
         skillToToggle: Skill
    ): Resource<List<Skill>>{

        val skillInList = selectedSkills.find{ it.name == skillToToggle.name}

        return if(skillInList != null){
            Resource.Success(selectedSkills - skillToToggle)
        }else if(selectedSkills.size >= ProfileConstants.MAX_SELECTED_SKILL_COUNT){
            Resource.Error(
                uiText = UiText.StringResource(
                    id = R.string.cannot_select_more_than_max_selected
                )
            )
        }else{
            Resource.Success(selectedSkills + skillToToggle)
        }
    }
}