package pw.coding.konnecto.core.domain.states

import pw.coding.konnecto.core.util.Error

data class PasswordTextFieldState(
    val text: String = "",
    val error: Error? = null,
    val isPasswordVisible: Boolean = false
)
