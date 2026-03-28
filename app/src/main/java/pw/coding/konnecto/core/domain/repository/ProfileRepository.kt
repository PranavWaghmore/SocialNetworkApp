package pw.coding.konnecto.core.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.domain.models.UserItem
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.feature_profile.domain.model.Profile
import pw.coding.konnecto.feature_profile.domain.model.Skill
import pw.coding.konnecto.feature_profile.domain.model.UpdateProfileData

interface ProfileRepository{

    suspend fun getProfile(userId: String): Resource<Profile>

    suspend fun searchUsers(query: String): Resource<List<UserItem>>

    suspend fun followUser(
        userId: String
    ): SimpleResource

    suspend fun unFollowUser(
        userId: String
    ): SimpleResource

    suspend fun getPostForProfile(
        page: Int = 0,
        pageSize : Int = Constants.DEFAULT_PAGE_SIZE,
        userId: String,
    ): Resource<List<Post>>

    suspend fun updateProfileData(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerImageUri: Uri?
        ): SimpleResource

    suspend fun getSkills(): Resource<List<Skill>>

    fun logout()
}