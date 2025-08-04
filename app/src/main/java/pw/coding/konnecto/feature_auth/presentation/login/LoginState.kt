package pw.coding.konnecto.feature_auth.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val isPasswordToggle: Boolean = false
)
