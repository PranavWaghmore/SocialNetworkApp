package pw.coding.konnecto.presentaion.login

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pw.coding.konnecto.R
import pw.coding.konnecto.presentaion.components.StandardTextField
import pw.coding.konnecto.presentaion.ui.theme.LargeSpace
import pw.coding.konnecto.presentaion.ui.theme.MediumSpace
import pw.coding.konnecto.presentaion.ui.theme.SmallSpace
import pw.coding.konnecto.presentaion.util.Screen


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = LargeSpace,
                end = LargeSpace,
                top = LargeSpace,
                bottom = 50.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
        ) {

            Text(
                text = stringResource(id = R.string.login),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayLarge,
            )
            Spacer(modifier = Modifier.height(SmallSpace))
            StandardTextField(
                text = viewModel.username.value,
                hint = stringResource(id = R.string.email),
                error = viewModel.userNameError.value,
                keyboardType = KeyboardType.Email,
                onValueChange = {
                    viewModel.setUserNameText(it)
                },
                leadingIcon = Icons.Default.Email
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            StandardTextField(
                text = viewModel.passwordText.value,
                hint = stringResource(id = R.string.password),
                error = viewModel.passwordError.value,
                isPasswordToggle = viewModel.showPassword.value,
                onPasswordToggleClick = {
                    viewModel.setShowPassword(it)
                },
                keyboardType = KeyboardType.Password,
                onValueChange = {
                    viewModel.setPasswordText(it)
                },
                leadingIcon = ImageVector.vectorResource(R.drawable.password)
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            Button(
                onClick = {
                    navController.navigate(Screen.MainFeedScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), // consistent height
                shape = MaterialTheme.shapes.medium, // rounded corners
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dont_have_an_account_yet))
                append(" ")
                val signUpText = stringResource(id = R.string.sign_up)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(signUpText)
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable(
                    onClick = {
                        navController.navigate(Screen.RegisterScreen.route)
                    }
                ),
            softWrap = true
        )
    }
}