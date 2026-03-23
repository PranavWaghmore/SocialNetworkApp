package pw.coding.konnecto.feature_post.domain.use_case

data class PostUseCases(
    val getPostForFollowsUseCase: GetPostForFollowsUseCase,
    val createPostUseCase: CreatePostUseCase,
    val getPostDetails: GetPostDetailsUseCase,
    val getComments: GetCommentsForPostUseCase,
    val addComment: AddCommentForPostUseCase,
)
