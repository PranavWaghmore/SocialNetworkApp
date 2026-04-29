package pw.coding.konnecto.feature_profile.presentation.util

import pw.coding.konnecto.core.util.Error

sealed class EditProfileError: Error() {
    object FieldEmpty: EditProfileError()
}