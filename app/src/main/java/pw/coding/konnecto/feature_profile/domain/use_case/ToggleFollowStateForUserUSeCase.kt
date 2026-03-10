package pw.coding.konnecto.feature_profile.domain.use_case

import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_profile.domain.repository.ProfileRepository

class ToggleFollowStateForUserUSeCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        userId: String,
        isFollowing: Boolean
    ): Resource<Unit>{
        return if(isFollowing){
            repository.unFollowUser(userId)
        }else{
            repository.followUser(userId)
        }
    }
}