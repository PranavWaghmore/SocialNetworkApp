package eu.tutorials.socialnetwork.presentaion.components

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
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.presentaion.util.TestTags


@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    maxLength: Int = 40,
    minLines : Int = 1,
    maxLines : Int = 1,
    style: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground
    ),
    singleLine: Boolean = true,
    error: String = "",
    leadingIcon: ImageVector? = null,
    showPasswordToggle: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = (keyboardType == KeyboardType.Password),
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            placeholder = {
                Text(
                    hint,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black
                    )
                )
            },
            isError = error.isNotEmpty(), // error != ""
            textStyle = style,
            visualTransformation = if (!showPasswordToggle && isPasswordToggleDisplayed) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            leadingIcon = if (leadingIcon != null) {
                {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            } else null,
            trailingIcon = if (isPasswordToggleDisplayed) {
                {
                    IconButton(
                        onClick = {
                            onPasswordToggleClick(!showPasswordToggle)
                        },
                        modifier = Modifier
                            .semantics {
                                testTag = TestTags.PASSWORD_TOGGLE
                            }
                    ) {
                        Icon(
                            imageVector = if (showPasswordToggle) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            },
                            tint = Color.White,
                            contentDescription = if (showPasswordToggle) {
                                stringResource(R.string.password_visible_content)
                            } else {
                                stringResource(R.string.password_hidden_content)
                            }
                        )
                    }
                }
            } else null,
        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End,
                modifier = modifier
            )
        }
    }
}