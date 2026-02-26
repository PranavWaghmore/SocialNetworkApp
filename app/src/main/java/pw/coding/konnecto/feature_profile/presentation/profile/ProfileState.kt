package pw.coding.konnecto.feature_profile.presentation.profile

import pw.coding.konnecto.feature_profile.domain.model.Profile

data class ProfileState (
    val profile: Profile ?= null,
    val isLoading: Boolean = false
)