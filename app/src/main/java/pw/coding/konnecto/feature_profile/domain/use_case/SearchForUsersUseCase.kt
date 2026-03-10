package pw.coding.konnecto.feature_profile.domain.use_case

import pw.coding.konnecto.core.domain.models.UserItem
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_profile.domain.repository.ProfileRepository

class SearchForUsersUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
         query: String
    ): Resource<List<UserItem>>{

        if(query.isBlank()){
           return  Resource.Success(emptyList())
        }
        return repository.searchUsers(query)
    }
}