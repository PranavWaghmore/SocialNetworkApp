package pw.coding.konnecto.core.domain.models

data class User(
    val userId : String,
    val profilePictureUrl : String,
    val username : String,
    val description : String,
    val followersCount : Int = 345,
    val followingCount : Int = 123,
    val postCount : Int = 86
)
