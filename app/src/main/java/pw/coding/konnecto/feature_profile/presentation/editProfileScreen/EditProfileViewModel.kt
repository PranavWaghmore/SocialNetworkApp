package pw.coding.konnecto.feature_profile.presentation.editProfileScreen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Rect
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.state.StandardTextFieldState
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_profile.domain.model.UpdateProfileData
import pw.coding.konnecto.feature_profile.domain.use_case.ProfileUseCases
import pw.coding.konnecto.feature_profile.presentation.SkillsSate
import pw.coding.konnecto.feature_profile.presentation.profile.ProfileState
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _githubTextFieldState = mutableStateOf(StandardTextFieldState())
    val githubTextFieldTextState: State<StandardTextFieldState> = _githubTextFieldState

    private val _instagramTextFieldState = mutableStateOf(StandardTextFieldState())
    val instagramTextFieldState: State<StandardTextFieldState> = _instagramTextFieldState

    private val _linkedinTextFieldState = mutableStateOf(StandardTextFieldState())
    val linkedinTextFieldState: State<StandardTextFieldState> = _linkedinTextFieldState

    private val _bioState = mutableStateOf(StandardTextFieldState())
    val bioState: State<StandardTextFieldState> = _bioState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _skills = mutableStateOf(SkillsSate())
    val skills: State<SkillsSate> = _skills

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _bannerUri = mutableStateOf<Uri?>(null)
    val bannerUri: State<Uri?> = _bannerUri

    private val _profilePictureUri = mutableStateOf<Uri?>(null)
    val profilePictureUri: State<Uri?> = _profilePictureUri

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            getSkills()
            getProfile(userId)
        }
    }

    private fun getSkills() {
        viewModelScope.launch {
            val result = profileUseCases.getSkills()
            when (result) {
                is Resource.Success -> {
                    _skills.value = skills.value.copy(
                        skills = result.data ?: kotlin.run {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    uiText = UiText.StringResource(R.string.couldnt_load_the_skills)
                                )
                            )
                            return@launch
                        }
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                    return@launch
                }
            }
        }
    }


    private fun getProfile(userId: String) {
        println("userId $userId")
        viewModelScope.launch {
            _profileState.value = profileState.value.copy(
                isLoading = true
            )
            val result = profileUseCases.getProfile(userId)
            when (result) {
                is Resource.Success -> {
                    val profile = result.data ?: kotlin.run {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                uiText = UiText.StringResource(R.string.couldnt_load_the_profile)
                            )
                        )
                        return@launch
                    }

                    _usernameState.value = usernameState.value.copy(
                        text = profile.username
                    )
                    _githubTextFieldState.value = _githubTextFieldState.value.copy(
                        text = profile.gitHubUrl ?: ""
                    )
                    _instagramTextFieldState.value = _instagramTextFieldState.value.copy(
                        text = profile.instagramUrl ?: ""
                    )
                    _linkedinTextFieldState.value = _linkedinTextFieldState.value.copy(
                        text = profile.linkedInUrl ?: ""
                    )
                    _bioState.value = bioState.value.copy(
                        text = profile.bio
                    )

                    _skills.value = skills.value.copy(
                        selectedSkills = profile.topSkills
                    )
                    _profileState.value = profileState.value.copy(
                        profile = profile,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    println("Error${result.data}")
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(uiText = result.uiText ?: UiText.unknownError())
                    )
                    _profileState.value = profileState.value.copy(
                        isLoading = false
                    )
                    return@launch
                }
            }

        }
    }

    private fun updateProfile() {
        viewModelScope.launch {
            val result = profileUseCases.updateProfile(
                updateProfileData = UpdateProfileData(
                    username = usernameState.value.text,
                    bio = bioState.value.text,
                    gitHubUrl = githubTextFieldTextState.value.text,
                    instagramUrl = instagramTextFieldState.value.text,
                    linkedInUrl = linkedinTextFieldState.value.text,
                    skills = skills.value.selectedSkills
                ),
                bannerUri = bannerUri.value,
                profilePictureUri = profilePictureUri.value
            )

            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = UiText.StringResource(R.string.profile_updated_successfully)
                        )
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.EnteredUsername -> {
                _usernameState.value = usernameState.value.copy(
                    text = event.value
                )
            }

            is EditProfileEvent.EnteredGitHubUrl -> {
                _githubTextFieldState.value = githubTextFieldTextState.value.copy(
                    text = event.value
                )
            }

            is EditProfileEvent.EnteredInstagramUrl -> {
                _instagramTextFieldState.value = instagramTextFieldState.value.copy(
                    text = event.value
                )
            }

            is EditProfileEvent.EnteredLinkedInUrl -> {
                _linkedinTextFieldState.value = linkedinTextFieldState.value.copy(
                    text = event.value
                )
            }

            is EditProfileEvent.EnteredBio -> {
                _bioState.value = bioState.value.copy(
                    text = event.value
                )
            }

            is EditProfileEvent.CropBannerImage -> {
                _bannerUri.value = event.uri
            }

            is EditProfileEvent.CropProfileImage -> {
                _profilePictureUri.value = event.uri
            }

            is EditProfileEvent.SetSkillSelected -> {

            }

            is EditProfileEvent.UpdateProfile -> {
                updateProfile()
            }
        }
    }
}