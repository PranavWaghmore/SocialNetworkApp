package pw.coding.konnecto.presentaion.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pw.coding.konnecto.R
import pw.coding.konnecto.presentaion.util.TestTags


@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    maxLength: Int = 40,
    minLines: Int = 1,
    maxLines: Int = 1,
    style: TextStyle = TextStyle(color = Color.Black),
    singleLine: Boolean = true,
    error: String = "",
    leadingIcon: ImageVector? = null,
    isPasswordToggle: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = (keyboardType == KeyboardType.Password),
    onValueChange: (String) -> Unit,
) {
    val visualTransformation = if (isPasswordToggleDisplayed && !isPasswordToggle) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = text,
            onValueChange = {
                if (it.length <= maxLength) onValueChange(it)
            },
            placeholder = {
                Text(
                    hint,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                )
            },
            isError = error.isNotEmpty(),
            textStyle = style,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            },
            trailingIcon = if (isPasswordToggleDisplayed) {
                {
                    IconButton(
                        onClick = { onPasswordToggleClick(!isPasswordToggle) },
                        modifier = Modifier.semantics { testTag = TestTags.PASSWORD_TOGGLE }
                    ) {
                        Icon(
                            imageVector = if (isPasswordToggle) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            },
                            tint = Color.Gray,
                            contentDescription = if (isPasswordToggle) {
                                stringResource(R.string.password_visible_content)
                            } else {
                                stringResource(R.string.password_hidden_content)
                            }
                        )
                    }
                }
            } else null,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White, // ✅ white background
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,

                unfocusedTextColor = Color.Black, // ✅ black text
                focusedTextColor = Color.Black,
                disabledTextColor = Color.Gray,
                errorTextColor = Color.Black,

                unfocusedPlaceholderColor = Color.Gray,
                focusedPlaceholderColor = Color.Gray
            )
        )
        if (error.isNotEmpty()) {
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
