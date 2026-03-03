package pw.coding.konnecto.feature_profile.domain.use_case

import android.net.Uri
import pw.coding.konnecto.R
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_profile.domain.model.UpdateProfileData
import pw.coding.konnecto.feature_profile.domain.repository.ProfileRepository

class UpdateProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerUri: Uri?
    ): SimpleResource{
        if(updateProfileData.username.isBlank()){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_username_field_empty)
            )
        }

        return repository.updateProfileData(
            updateProfileData= updateProfileData,
            profilePictureUri = profilePictureUri,
            bannerImageUri = bannerUri
        )
    }
}