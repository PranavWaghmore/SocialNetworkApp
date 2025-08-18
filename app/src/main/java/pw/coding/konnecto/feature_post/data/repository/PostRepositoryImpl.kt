package pw.coding.konnecto.feature_post.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import kotlinx.io.IOException
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_post.data.dat_source.PostApi
import pw.coding.konnecto.feature_post.domain.repository.PostRepository
import retrofit2.HttpException

class PostRepositoryImpl(
    private val api: PostApi
) : PostRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getPostsForFollows(
        page: Int,
        pageSize: Int
    ): Resource<List<Post>> {
        return try {
            val posts = api.getPostsForFollows(page, pageSize)
            Resource.Success(posts)
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

