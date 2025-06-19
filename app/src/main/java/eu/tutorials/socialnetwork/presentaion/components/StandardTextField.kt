package eu.tutorials.socialnetwork.presentaion.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.presentaion.ui.theme.HintGray


@Composable
fun StandardTextField(
    text: String = "",
    hint: String = "",
    maxLength: Int=40,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    val isPasswordToggleDisplayed by remember {
        mutableStateOf(keyboardType == KeyboardType.Password)
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    TextField(
        value = text,
        onValueChange = {
            if(it.length <= maxLength){
                onValueChange(it)
            }
        },
        placeholder = {
            Text(
                hint,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = HintGray
                )
            )
        },
        isError = isError,
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        visualTransformation = if(!isPasswordVisible && isPasswordToggleDisplayed){
            PasswordVisualTransformation()
        }else{
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        trailingIcon = {
            if(isPasswordToggleDisplayed){
                IconButton(onClick = {
                    isPasswordVisible=!isPasswordVisible
                }) {
                    Icon(
                        imageVector = if(isPasswordVisible) {
                            Icons.Filled.VisibilityOff
                    }
                        else{
                          Icons.Filled.Visibility
                        },
                        contentDescription = if(isPasswordVisible){
                            stringResource(R.string.password_visible_content)
                        }
                        else{
                            stringResource(R.string.password_hidden_content)
                        }
                    )
                }
            }

        },
        modifier = Modifier.fillMaxWidth()
    )
}