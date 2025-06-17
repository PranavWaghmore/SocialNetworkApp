package eu.tutorials.socialnetwork.presentaion.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun StandardTextField(
    text: String = "",
    hint: String = "",
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    val isPasswordToggleDisplayed by remember {
        mutableStateOf(keyboardType == KeyboardType.Password)
    }
    val isPasswordVisible by remember {
        mutableStateOf(false)
    }
    TextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                hint,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        trailingIcon = {
            if(isPasswordToggleDisplayed){
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = if(isPasswordVisible) {
                            Icons.Filled.VisibilityOff
                    }
                        else{
                          Icons.Filled.Visibility
                        },
                        contentDescription = ""
                    )
                }
            }

        },
        modifier = Modifier.fillMaxWidth()
    )
}