package pw.coding.konnecto.core.domain.models

data class User(
    val profilePictureUrl : String,
    val username : String,
    val description : String,
    val onFollowersCount : Int = 345,
    val onFollowingCount : Int = 123,
    val onPostCount : Int = 86
)
