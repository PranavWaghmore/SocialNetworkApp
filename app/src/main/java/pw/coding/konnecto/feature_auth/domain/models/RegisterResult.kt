package pw.coding.konnecto.feature_auth.domain.models

import pw.coding.konnecto.core.util.SimpleResource
import pw.coding.konnecto.feature_auth.presentation.util.AuthError

data class RegisterResult(
    val emailError: AuthError? = null,
    val usernameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)
