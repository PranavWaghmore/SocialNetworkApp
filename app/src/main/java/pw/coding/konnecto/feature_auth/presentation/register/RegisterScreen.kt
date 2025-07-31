package pw.coding.konnecto.feature_auth.presentation.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import pw.coding.konnecto.R
import pw.coding.konnecto.core.presentation.components.StandardTextField
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.presentation.ui.theme.SmallSpace
import pw.coding.konnecto.core.presentation.util.asString
import pw.coding.konnecto.feature_auth.presentation.util.AuthError


@Composable
fun RegisterScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val usernameState = viewModel.usernameState.value
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val registerState = viewModel.registerState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is RegisterViewModel.UiEvent.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

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
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(SmallSpace))
            StandardTextField(
                text = emailState.text,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredEmail(it))
                },
                hint = stringResource(id = R.string.email),
                error = when (emailState.error) {
                    is AuthError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    is AuthError.InvalidEmail -> {
                        stringResource(id = R.string.not_a_valid_email)
                    }
                    else -> ""
                },
                keyboardType = KeyboardType.Email,
                leadingIcon = Icons.Default.Email
            )
            Spacer(modifier = Modifier.height(SmallSpace))
            StandardTextField(
                text = usernameState.text,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredUsername(it))
                },
                error = when(usernameState.error){
                    is AuthError.FieldEmpty -> {
                        stringResource(R.string.this_field_cant_be_empty)
                    }
                    is AuthError.InputTooShort -> {
                        stringResource(R.string.input_too_short)
                    }
                    else -> ""
                },
                hint = stringResource(id = R.string.username),
                leadingIcon = ImageVector.vectorResource(R.drawable.user)
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            StandardTextField(
                text = passwordState.text,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredPassword(it))
                },
                hint = stringResource(id = R.string.password),
                error = when(passwordState.error){
                    is AuthError.FieldEmpty -> {
                        stringResource(R.string.this_field_cant_be_empty)
                    }
                    is AuthError.InputTooShort -> {
                        stringResource(R.string.input_too_short)
                    }
                    is AuthError.InvalidPassword -> {
                        stringResource(R.string.invalid_password)
                    }
                    else -> ""
                },
                isPasswordToggle =  passwordState.isPasswordVisible,
                keyboardType = KeyboardType.Password,
                onPasswordToggleClick = {
                    viewModel.onEvent(RegisterEvent.TogglePasswordVisibility)
                },
                leadingIcon = ImageVector.vectorResource(R.drawable.password)
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            Button(
                onClick = {
                    viewModel.onEvent(RegisterEvent.Register)
                          },
                enabled = !registerState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                if (registerState.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                } else {
                    Text(
                        text = stringResource(R.string.Register),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
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