package pw.coding.konnecto.presentaion.create_post

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pw.coding.konnecto.R
import pw.coding.konnecto.presentaion.components.StandardTextField
import pw.coding.konnecto.presentaion.components.StandardToolBar
import pw.coding.konnecto.presentaion.ui.theme.LargeSpace
import pw.coding.konnecto.presentaion.ui.theme.MediumSpace
import pw.coding.konnecto.presentaion.ui.theme.SmallSpace
import pw.coding.konnecto.presentaion.util.state.StandardTextFieldState

@Composable
fun CreatePostScreen(
    navController: NavController,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            navController = navController,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.create_post)
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LargeSpace)
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(SmallSpace)
                    )
                    .clickable{

                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(MediumSpace))
            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = viewModel.descriptionState.value.text,
                hint = stringResource(id = R.string.description),
                singleLine = false,
                minLines = 3,
                maxLines = 7,
                leadingIcon = (Icons.Default.PostAdd),
                error = viewModel.descriptionState.value.error,
                onValueChange = {
                    viewModel.setDescriptionState(
                        StandardTextFieldState(text = it)
                    )
                }
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            IconButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        shape = RoundedCornerShape(15.dp),
                        color = MaterialTheme.colorScheme.primary
                    ),
                colors = IconButtonDefaults.iconButtonColors(
                    MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(R.string.post),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}