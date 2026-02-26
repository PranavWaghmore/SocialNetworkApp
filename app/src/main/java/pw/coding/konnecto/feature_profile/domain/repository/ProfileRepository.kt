package pw.coding.konnecto.feature_profile.domain.repository

import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_profile.domain.model.Profile

interface ProfileRepository{

    suspend fun getProfile(userId: String): Resource<Profile>
}