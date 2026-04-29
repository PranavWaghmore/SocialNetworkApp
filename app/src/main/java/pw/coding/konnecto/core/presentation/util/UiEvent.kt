package pw.coding.konnecto.core.presentation.util

import pw.coding.konnecto.core.util.UiText


sealed class UiEvent {
    data class ShowSnackbar(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
    object OnLogin: UiEvent()
}