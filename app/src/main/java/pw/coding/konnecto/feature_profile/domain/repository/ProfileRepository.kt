package pw.coding.konnecto.feature_profile.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.feature_profile.domain.model.Profile
import pw.coding.konnecto.feature_profile.domain.model.Skill
import pw.coding.konnecto.feature_profile.domain.model.UpdateProfileData

interface ProfileRepository{

    suspend fun getProfile(userId: String): Resource<Profile>

     fun getPostForProfile(
        userId: String
    ): Flow<PagingData<Post>>

    suspend fun updateProfileData(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerImageUri: Uri?
        ): SimpleResource

    suspend fun getSkills(): Resource<List<Skill>>
}