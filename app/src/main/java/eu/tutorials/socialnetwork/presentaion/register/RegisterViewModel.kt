package eu.tutorials.socialnetwork.presentaion.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel()  {
    private val _usernameText= mutableStateOf("")
    val username : State<String> =_usernameText

    private val _passwordText=mutableStateOf("")
    val passwordText: State<String> =_passwordText

    private val _showPassword=mutableStateOf(false)
    val showPassword: State<Boolean> = _showPassword

    fun setUserNameText(username: String){
        _usernameText.value=username
    }

    fun setPasswordText(password : String){
        _passwordText.value=password
    }

    fun setShowPassword(showPassword: Boolean){
        _showPassword.value= showPassword
    }
}