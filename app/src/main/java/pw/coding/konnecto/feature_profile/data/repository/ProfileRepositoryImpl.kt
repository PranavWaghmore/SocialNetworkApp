package pw.coding.konnecto.feature_profile.data.repository

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import pw.coding.konnecto.R
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.core.util.uriToTempFile
import pw.coding.konnecto.feature_profile.data.remote.ProfileApi
import pw.coding.konnecto.feature_profile.domain.model.Profile
import pw.coding.konnecto.feature_profile.domain.model.Skill
import pw.coding.konnecto.feature_profile.domain.model.UpdateProfileData
import pw.coding.konnecto.feature_profile.domain.repository.ProfileRepository
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val api: ProfileApi,
    private val gson: Gson,
    private val context: Context
) : ProfileRepository {

    override suspend fun getProfile(userId: String): Resource<Profile> {

        return try {
            val response = api.getProfile(userId)
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

    override suspend fun updateProfileData(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerImageUri: Uri?
    ): SimpleResource {
        return try {
            val response = api.updateProfile(
                bannerImage = bannerImageUri?.let { uri ->
                    val file = uriToTempFile(context,uri)
                    MultipartBody.Part
                        .createFormData(
                            "banner_image",
                            file.name,
                            file.asRequestBody()
                        )
                },
                profilePicture = profilePictureUri?.let { uri ->
                    val file = uriToTempFile(context,uri)
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
            val response = api.getSkills()
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
}