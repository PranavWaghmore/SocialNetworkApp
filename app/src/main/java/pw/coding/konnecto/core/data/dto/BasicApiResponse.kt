package pw.coding.konnecto.core.data.dto

data class BasicApiResponse(
    val successful: Boolean,
    val message: String ?=null
)
