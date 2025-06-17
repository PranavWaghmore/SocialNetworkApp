package eu.tutorials.socialnetwork.presentaion.login

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.presentaion.components.StandardTextField
import eu.tutorials.socialnetwork.presentaion.ui.theme.MediumSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.MediumSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.SmallSpace


@Composable
fun LoginScreen (
    navController: NavController ,
    viewModel: LoginViewModel= hiltViewModel()
){
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = MediumSpace),
    ){

        Text(
            text= stringResource(id=R.string.login),
            color= MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(SmallSpace))
        StandardTextField(
            text = viewModel.username.value,
            onValueChange = {
                    viewModel.setUserNameText(it)
            },
            hint = stringResource(id = R.string.username_email)
        )
        Spacer(modifier = Modifier.height(SmallSpace))
        StandardTextField(
            text = viewModel.passwordText.value,
            onValueChange = {
                viewModel.setPasswordText(it)
            },
            hint = stringResource(id = R.string.password) ,
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.height(SmallSpace))
        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text(
                text = stringResource(id = R.string.login)
            )
        }


    }
}