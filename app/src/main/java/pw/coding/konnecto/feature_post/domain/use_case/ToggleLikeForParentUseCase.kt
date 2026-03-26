package pw.coding.konnecto.feature_post.domain.use_case

import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_post.domain.repository.PostRepository

class ToggleLikeForParentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        isLiked: Boolean,
        parentId: String,
        parentType:Int
    ): Resource<Unit>{
        return if(isLiked){
            repository.unlikeParent(
                parentId,
                parentType
            )
        }else{
            repository.likeParent(
                parentId,
                parentType
            )
        }
    }
}