package pw.coding.konnecto.core.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import pw.coding.konnecto.R
import pw.coding.konnecto.core.data.paging.PostSource
import pw.coding.konnecto.core.data.remote.PostApi
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.domain.models.UserItem
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.core.util.uriToTempFile
import pw.coding.konnecto.feature_profile.data.remote.ProfileApi
import pw.coding.konnecto.feature_profile.data.request.FollowUpdateRequest
import pw.coding.konnecto.feature_profile.domain.model.Profile
import pw.coding.konnecto.feature_profile.domain.model.Skill
import pw.coding.konnecto.feature_profile.domain.model.UpdateProfileData
import pw.coding.konnecto.core.domain.repository.ProfileRepository
import retrofit2.HttpException
import java.io.IOException
import androidx.core.content.edit

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi,
    private val postApi: PostApi,
    private val gson: Gson,
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) : ProfileRepository {

    override suspend fun getProfile(userId: String): Resource<Profile> {

        return try {
            val response = profileApi.getProfile(userId)
            if (response.successful) {
                Resource.Success(response.data?.toProfile())
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun searchUsers(query: String): Resource<List<UserItem>> {
        return try {
            val response = profileApi.searchUsers(query)
            Resource.Success(data =response.map { it.toUserItem() })
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun followUser(userId: String): SimpleResource {
        return try {
            val response = profileApi.followUser(FollowUpdateRequest(userId))
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun unFollowUser(userId: String): SimpleResource {
        return try {
            val response = profileApi.unFollowUser(userId)
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override suspend fun getPostForProfile(
        page: Int,
        pageSize: Int,
        userId: String
    ): Resource<List<Post>> {
        return try {
            val response = postApi.getPostsForProfile(
                userId = userId,
                page = page,
               pageSize =  pageSize
            )
            Resource.Success(data = response)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }


    override suspend fun updateProfileData(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerImageUri: Uri?
    ): SimpleResource {
        return try {
            val response = profileApi.updateProfile(
                bannerImage = bannerImageUri?.let { uri ->
                    val file = uriToTempFile(context, uri)
                    MultipartBody.Part
                        .createFormData(
                            "banner_image",
                            file.name,
                            file.asRequestBody()
                        )
                },
                profilePicture = profilePictureUri?.let { uri ->
                    val file = uriToTempFile(context, uri)
                    MultipartBody.Part
                        .createFormData(
                            "profile_picture",
                            file.name,
                            file.asRequestBody()
                        )
                },
                updateProfileData = MultipartBody.Part
                    .createFormData(
                        "update_profile_data",
                        gson.toJson(updateProfileData)
                    )
            )
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }


    override suspend fun getSkills(): Resource<List<Skill>> {
        return try {
            val response = profileApi.getSkills()
            Resource.Success(
                data = response.map { it.toSkill() }
            )
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }

    override fun logout() {
        sharedPreferences.edit {
            remove(Constants.KEY_JWT_TOKEN)
                remove(Constants.KEY_USER_ID)
        }
    }


}