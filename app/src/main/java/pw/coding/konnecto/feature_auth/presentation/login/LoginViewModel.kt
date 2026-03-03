package pw.coding.konnecto.feature_auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import pw.coding.konnecto.core.util.Screen
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_auth.domain.use_case.LoginUseCase
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _passwordState = mutableStateOf(StandardTextFieldState())
    val passwordState: State<StandardTextFieldState> = _passwordState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val evenFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.value
                )
            }
            is LoginEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }
            LoginEvent.TogglePasswordVisibility -> {
                _loginState.value = _loginState.value.copy(
                    isPasswordVisible = !loginState.value.isPasswordVisible
                )
            }
            LoginEvent.Login -> {
                viewModelScope.launch {
                    if (loginState.value.isLoading) {return@launch}
                    _emailState.value = emailState.value.copy(error = null)
                    _passwordState.value = passwordState.value.copy(error = null)
                    _loginState.value = LoginState(isLoading = true)
                    val loginResult = loginUseCase(
                        email = _emailState.value.text,
                        password = _passwordState.value.text
                    )
                    if (loginResult.emailError != null || loginResult.passwordError != null) {
                        _emailState.value = emailState.value.copy(error = loginResult.emailError)
                        _passwordState.value = passwordState.value.copy(error = loginResult.passwordError)
                        _loginState.value = LoginState(isLoading = false)
                        return@launch
                    }

                    when(loginResult.result){
                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    uiText = UiText.StringResource(R.string.login_successfully)
                                )
                            )
                            _eventFlow.emit(
                                UiEvent.Navigate(Screen.MainFeedScreen.route)
                            )
                            _loginState.value = LoginState(isLoading = false)
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    uiText = loginResult.result.uiText
                                        ?: UiText.unknownError()
                                )
                            )
                            _loginState.value = LoginState(isLoading = false)
                        }
                        null -> {
                            print("null")
                        }
                    }
                }
            }
        }
    }
}