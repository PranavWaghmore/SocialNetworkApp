package pw.coding.konnecto.feature_post.data.repository

import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import dagger.hilt.android.internal.Contexts
import io.ktor.http.content.MultiPartData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.domain.util.getFileName
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_post.data.dat_source.PostApi
import pw.coding.konnecto.feature_post.data.dat_source.request.CreatePostRequest
import pw.coding.konnecto.feature_post.data.paging.PostSource
import pw.coding.konnecto.feature_post.domain.repository.PostRepository
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class PostRepositoryImpl(
    private val api: PostApi,
    private val gson: Gson,
    private val appContext : Context
) : PostRepository {
    override val posts: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)){
            PostSource(api)
        }.flow

    override suspend fun createPost(description: String, imageUri: Uri): SimpleResource {
        val request = CreatePostRequest(description,imageUri)
        val file = withContext(Dispatchers.IO){
            appContext.contentResolver.openFileDescriptor(imageUri,"r") ?.let { fd ->
                val inputStream = FileInputStream(fd.fileDescriptor)
                val file = File(
                    appContext.cacheDir,
                    appContext.contentResolver.getFileName(imageUri)
                )
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                file
            }
        }?:
        return Resource.Error(
            uiText = UiText.StringResource(R.string.error_file_not_found)
        )
        return try {
            val response = api.createPost(
                postData = MultipartBody.Part.createFormData(
                    "post_data",
                    gson.toJson(request)
                ),
                postImage = MultipartBody.Part.createFormData(
                    name = "post_image",
                    filename = file.name,
                    body = file.asRequestBody()
                )
            )
            if(response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        } catch(e: java.io.IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.coudnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.something_went_wrong)
            )
        }
    }


}

