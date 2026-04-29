package pw.coding.konnecto.feature_auth.presentation.util

import pw.coding.konnecto.core.util.Error

sealed class AuthError: Error() {
    object FieldEmpty: AuthError()
    object InputTooShort: AuthError()
    object InvalidEmail: AuthError()
    object InvalidPassword: AuthError()
}