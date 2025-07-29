package pw.coding.konnecto.feature_auth.presentation.register

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pw.coding.konnecto.core.domain.state.PasswordTextFieldState
import pw.coding.konnecto.core.domain.state.StandardTextFieldState
import pw.coding.konnecto.core.util.Constants
import pw.coding.konnecto.feature_auth.presentation.util.AuthError
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel()  {
    private val _emailState= mutableStateOf(StandardTextFieldState())
    val emailState : State<StandardTextFieldState> = _emailState

    private val _usernameState= mutableStateOf(StandardTextFieldState())
    val usernameState : State<StandardTextFieldState> = _usernameState

    private val _passwordState= mutableStateOf(PasswordTextFieldState())
    val passwordState : State<PasswordTextFieldState> = _passwordState

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
                validateUsername(username = usernameState.value.text)
                validateEmail(email = emailState.value.text)
                validatePassword(password = passwordState.value.text)
            }
        }
    }

    private fun validateUsername(username: String){
        val trimmedUsername = username.trim()
        if(trimmedUsername.isBlank()){
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if(trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.InputTooShort
            )
            return
        }
        _usernameState.value = _usernameState.value.copy(error = null)
    }

    private fun validateEmail(email: String) {
        val trimmedEmail = email.trim()
        if(trimmedEmail.isBlank()) {
            _emailState.value = _emailState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailState.value = _emailState.value.copy(
                error = AuthError.InvalidEmail
            )
            return
        }
        _emailState.value = _emailState.value.copy(error = null)
    }

    private fun validatePassword(password: String) {
        if(password.isBlank()) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if(password.length < Constants.MIN_PASSWORD_LENGTH) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InputTooShort
            )
            return
        }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if(!capitalLettersInPassword || !numberInPassword) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InvalidPassword
            )
            return
        }
        _passwordState.value = _passwordState.value.copy(error = null)
    }
}