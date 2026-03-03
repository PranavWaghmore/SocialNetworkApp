package pw.coding.konnecto.feature_auth.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.state.PasswordTextFieldState
import pw.coding.konnecto.core.domain.state.StandardTextFieldState
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.Screen
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_auth.domain.use_case.RegisterUseCase
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private val _registerState = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: RegisterEvent) {
        when (event) {
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

            RegisterEvent.Register -> {
                register()
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            _usernameState.value = usernameState.value.copy(error = null)
            _emailState.value = emailState.value.copy(error = null)
            _passwordState.value = passwordState.value.copy(error = null)
            _registerState.value = _registerState.value.copy(isLoading = true)

            val registerResult = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text
            )

            // Check for validation errors
            if (registerResult.emailError != null || registerResult.usernameError != null ||
                registerResult.passwordError != null
            ) {
                _emailState.value =
                    emailState.value.copy(error = registerResult.emailError)
                _usernameState.value =
                    usernameState.value.copy(error = registerResult.usernameError)
                _passwordState.value =
                    passwordState.value.copy(error = registerResult.passwordError)
                _registerState.value = RegisterState(isLoading = false)
                return@launch
            }

            when (registerResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = UiText.StringResource(
                                R.string.account_created_succesfully_you_can_login_now
                            )
                        )
                    )
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.LoginScreen.route)
                    )
                    _registerState.value = RegisterState(isLoading = false)
                    _usernameState.value = StandardTextFieldState()
                    _emailState.value = StandardTextFieldState()
                    _passwordState.value = PasswordTextFieldState()
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = registerResult.result.uiText ?: UiText.unknownError()
                        )
                    )
                    _registerState.value = RegisterState(isLoading = false)
                }
                else -> {
                    _registerState.value = RegisterState(isLoading = false)
                }
            }
        }
    }
}