package eu.tutorials.socialnetwork.presentaion.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun StandardTextField(
    text: String ="",
    hint : String="" ,
    onValueChange:(String) -> Unit
) {
    TextField(
        value =text ,
        onValueChange = onValueChange ,
        placeholder = {
            Text(hint , style = MaterialTheme.typography.bodyLarge)
        } ,
        modifier = Modifier.fillMaxWidth()
    )
}