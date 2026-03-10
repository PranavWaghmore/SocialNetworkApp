package pw.coding.konnecto.feature_profile.presentation.search

import pw.coding.konnecto.core.util.Error
import pw.coding.konnecto.core.util.UiText

data class SearchError(
     val message : UiText
): Error()
