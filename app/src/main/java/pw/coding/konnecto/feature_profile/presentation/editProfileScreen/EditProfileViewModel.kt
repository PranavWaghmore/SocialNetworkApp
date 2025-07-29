package pw.coding.konnecto.feature_profile.presentation.editProfileScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pw.coding.konnecto.core.domain.state.StandardTextFieldState
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject
constructor() : ViewModel() {
    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _githubTextFieldState = mutableStateOf(StandardTextFieldState())
    val githubTextFieldTextState : State<StandardTextFieldState> = _githubTextFieldState

    private val _instagramTextFieldState = mutableStateOf(StandardTextFieldState())
    val instagramTextFieldState : State<StandardTextFieldState> = _instagramTextFieldState

    private val _linkedinTextFieldState = mutableStateOf(StandardTextFieldState())
    val LinkedinTextFieldState : State<StandardTextFieldState> = _linkedinTextFieldState

    private val _bioState = mutableStateOf(StandardTextFieldState())
    val bioState : State<StandardTextFieldState> = _bioState

    fun setUsernameState(state: StandardTextFieldState){
        _usernameState.value = state
    }

    fun setGithubTextFieldState(state  : StandardTextFieldState){
        _githubTextFieldState.value = state
    }

    fun steInstagramTextFieldState(state  : StandardTextFieldState){
        _instagramTextFieldState.value = state
    }

    fun setLeetcodeTExtFieldState(state  : StandardTextFieldState){
        _linkedinTextFieldState.value = state
    }

    fun setBioState(state  : StandardTextFieldState){
        _bioState.value = state
    }
}