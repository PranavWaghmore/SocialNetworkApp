package pw.coding.konnecto.feature_profile.domain.use_case

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val getSkills: GetSkillsUseCase,
    val updateProfile: UpdateProfileUseCase,
    val setSkillSelected: SetSkillSelectedUseCase,
    val getPostsForProfile: GetPostsForProfileUseCase,
    val searchUsers : SearchForUsersUseCase,
    val toggleFollowStateForUser: ToggleFollowStateForUserUSeCase
)
