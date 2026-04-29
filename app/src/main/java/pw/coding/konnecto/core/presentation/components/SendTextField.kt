package pw.coding.konnecto.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.stringResource
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.states.StandardTextFieldState
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace
import pw.coding.konnecto.core.presentation.ui.theme.SmallSpace
import pw.coding.konnecto.core.presentation.ui.theme.TextWhite

@Composable
fun SendTextField(
    state: StandardTextFieldState,
    focusRequester: FocusRequester = FocusRequester(),
    onValueChange: (String) -> Unit =  {},
    onSendClick: () -> Unit = {}
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(LargeSpace),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StandardTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            text = state.text,
            hint = stringResource(R.string.enter_comment),
            onValueChange =  onValueChange
        )
        Spacer(modifier = Modifier.width(SmallSpace))


        IconButton(
            onClick = onSendClick,
            colors = IconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Black,
                disabledContentColor = Black,
                disabledContainerColor = Black
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send Comment",
                tint = TextWhite
            )
        }
    }
}