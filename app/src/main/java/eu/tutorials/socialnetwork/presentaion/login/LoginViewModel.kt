package eu.tutorials.socialnetwork.presentaion.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel()  {
    private val _usernameText= mutableStateOf("")
    val username : State<String> =_usernameText

    private val _passwordText=mutableStateOf("")
    val passwordText: State<String> =_passwordText

    private val _userNameError=mutableStateOf("")
    val userNameError: State<String> = _userNameError

    private val _passwordError=mutableStateOf("")
    val passwordError: State<String> = _passwordError

    private val _showPassword=mutableStateOf(false)
    val showPassword: State<Boolean> = _showPassword

    fun setUserNameText(username: String){
        _usernameText.value=username
    }

    fun setPasswordText(password : String){
        _passwordText.value=password
    }

    fun setIsUserNameError(error: String){
        _userNameError.value= error
    }

    fun setIsPasswordError(error: String){
        _passwordError.value= error
    }

    fun setShowPassword(showPassword: Boolean){
        _showPassword.value= showPassword
    }

}