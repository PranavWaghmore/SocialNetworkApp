package pw.coding.konnecto.presentaion.register

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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


@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
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
                text = stringResource(id = R.string.Register),
                //color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(SmallSpace))
            StandardTextField(
                text = viewModel.emailText.value,
                hint = stringResource(id = R.string.email),
                error = viewModel.emailError.value,
                keyboardType = KeyboardType.Email,
                onValueChange = {
                    viewModel.setEmailText(it)
                },
            )
            Spacer(modifier = Modifier.height(SmallSpace))
            StandardTextField(
                text = viewModel.username.value,
                hint = stringResource(id = R.string.username),
                error = viewModel.userNameError.value,
                onValueChange = {
                    viewModel.setUserNameText(it)
                },
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            StandardTextField(
                text = viewModel.passwordText.value,
                hint = stringResource(id = R.string.password),
                error = viewModel.passwordError.value,
                showPasswordToggle = viewModel.showPassword.value,
                onPasswordToggleClick = {
                    viewModel.setShowPassword(it)
                },
                keyboardType = KeyboardType.Password,
                onValueChange = {
                    viewModel.setPasswordText(it)
                },
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            Button(
                modifier = Modifier
                    .align(Alignment.End),
                onClick = {

                }
            ) {
                Text(
                    text = stringResource(R.string.Register),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.Already_have_an_Account))
                append(" ")
                val signInText = stringResource(id = R.string.sign_in)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(signInText)
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable(
                    onClick = {
                        navController.popBackStack()
                    }
                ),
            softWrap = true
        )
    }
}