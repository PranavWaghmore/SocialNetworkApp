package pw.coding.konnecto.feature_profile.domain.use_case

import pw.coding.konnecto.core.domain.use_case.ToggleFollowStateForUserUSeCase

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val getSkills: GetSkillsUseCase,
    val updateProfile: UpdateProfileUseCase,
    val setSkillSelected: SetSkillSelectedUseCase,
    val getPostsForProfile: GetPostsForProfileUseCase,
    val searchUsers : SearchForUsersUseCase,
    val toggleFollowStateForUser: ToggleFollowStateForUserUSeCase,
    val logout: LogoutUseCase,
)
