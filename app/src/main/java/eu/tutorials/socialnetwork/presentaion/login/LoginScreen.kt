package eu.tutorials.socialnetwork.presentaion.login

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
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.presentaion.components.StandardTextField
import eu.tutorials.socialnetwork.presentaion.ui.theme.LargeSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.MediumSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.SmallSpace


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
            Spacer(modifier = Modifier.height(MediumSpace))
            StandardTextField(
                text = viewModel.passwordText.value,
                onValueChange = {
                    viewModel.setPasswordText(it)
                },
                hint = stringResource(id = R.string.password),
                keyboardType = KeyboardType.Password
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            Button(
                onClick = {

                },
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.login)
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
            modifier = Modifier.align(Alignment.BottomCenter),
            softWrap = true
        )
    }
}