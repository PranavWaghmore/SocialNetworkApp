package pw.coding.konnecto.feature_auth.presentation.register

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pw.coding.konnecto.core.domain.state.PasswordTextFieldState
import pw.coding.konnecto.core.domain.state.StandardTextFieldState
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.feature_auth.domain.use_case.RegisterUseCase
import pw.coding.konnecto.feature_auth.presentation.util.AuthError
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import pw.coding.konnecto.R
import pw.coding.konnecto.core.util.UiText


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel()  {
    private val _emailState= mutableStateOf(StandardTextFieldState())
    val emailState : State<StandardTextFieldState> = _emailState

    private val _usernameState= mutableStateOf(StandardTextFieldState())
    val usernameState : State<StandardTextFieldState> = _usernameState

    private val _passwordState= mutableStateOf(PasswordTextFieldState())
    val passwordState : State<PasswordTextFieldState> = _passwordState

    private val _registerState = mutableStateOf(RegisterState())
    val registerState : State<RegisterState> = _registerState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent){
        when(event){
            is RegisterEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredUsername -> {
                _usernameState.value = _usernameState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }
            RegisterEvent.TogglePasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
            RegisterEvent.Register ->{
                val isUsernameValid = validateUsername(usernameState.value.text)
                val isEmailValid = validateEmail(emailState.value.text)
                val isPasswordValid = validatePassword(passwordState.value.text)

                if (isUsernameValid && isEmailValid && isPasswordValid) {
                    registerIfNoErrors()
                }
            }
        }
    }


    private fun registerIfNoErrors(){
        if(emailState.value.error != null || usernameState.value.error != null ||
            passwordState.value.error != null){
            return
        }
        viewModelScope.launch {
            _registerState.value = RegisterState(isLoading = true)
            val result = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text
            )
            _registerState.value = RegisterState(isLoading = false)
            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(
                            UiText.StringResource(R.string.successfully_registered)
                        )
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(result.uiText ?: UiText.unknownError())
                    )
                }
            }
        }
    }

    private fun validateUsername(username: String): Boolean{
        val trimmedUsername = username.trim()
        if(trimmedUsername.isBlank()){
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.FieldEmpty
            )
            return false
        }
        if(trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.InputTooShort
            )
            return false
        }
        _usernameState.value = _usernameState.value.copy(error = null)
        return true
    }

    private fun validateEmail(email: String): Boolean{
        val trimmedEmail = email.trim()
        if(trimmedEmail.isBlank()) {
            _emailState.value = _emailState.value.copy(
                error = AuthError.FieldEmpty
            )
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailState.value = _emailState.value.copy(
                error = AuthError.InvalidEmail
            )
            return false
        }
        _emailState.value = _emailState.value.copy(error = null)
        return true
    }

    private fun validatePassword(password: String): Boolean {
        if(password.isBlank()) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.FieldEmpty
            )
            return false
        }
        if(password.length < Constants.MIN_PASSWORD_LENGTH) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InputTooShort
            )
            return false
        }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if(!capitalLettersInPassword || !numberInPassword) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InvalidPassword
            )
            return false
        }
        _passwordState.value = _passwordState.value.copy(error = null)
        return true
    }

    sealed class UiEvent {
        data class SnackbarEvent(val uiText: UiText) : UiEvent()
    }
}