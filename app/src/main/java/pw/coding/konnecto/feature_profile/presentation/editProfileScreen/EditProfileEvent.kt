package pw.coding.konnecto.feature_profile.presentation.editProfileScreen

import android.net.Uri
import pw.coding.konnecto.feature_profile.domain.model.Skill


sealed class EditProfileEvent {
    data class EnteredUsername(val value: String): EditProfileEvent()
    data class EnteredGitHubUrl(val value: String): EditProfileEvent()
    data class EnteredInstagramUrl(val value: String): EditProfileEvent()
    data class EnteredLinkedInUrl(val value: String): EditProfileEvent()
    data class EnteredBio(val value: String): EditProfileEvent()

    data class CropProfileImage(val uri: Uri?): EditProfileEvent()
    data class CropBannerImage(val uri: Uri?): EditProfileEvent()

    data class SetSkillSelected(val skill: Skill): EditProfileEvent()

    object UpdateProfile: EditProfileEvent()

}