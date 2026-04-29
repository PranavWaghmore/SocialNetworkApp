package pw.coding.konnecto.feature_post.presentation.create_post

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.flow.collectLatest
import pw.coding.konnecto.R
import pw.coding.konnecto.core.presentation.components.StandardTextField
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.presentation.ui.theme.SmallSpace
import pw.coding.konnecto.core.presentation.util.CropActivityResultContract
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.presentation.util.asString
import pw.coding.konnecto.feature_post.util.PostConstants
import pw.coding.konnecto.feature_post.util.PostDescriptionError

@Composable
fun CreatePostScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: CreatePostViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState
) {
    val imageUri = viewModel.chosenImageUri.value
    val descriptionState = viewModel.descriptionState.value

    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(16f,9f)
    ) {
        viewModel.onEvent(CreatePostEvent.CropImage(it))
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri->
        uri?.let {
            cropActivityLauncher.launch(it)
        }
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
                else -> {}
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
           onNavigateUp =  onNavigateUp,
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
                    .clip(RoundedCornerShape(SmallSpace))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(SmallSpace)
                    )
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )

                imageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(uri)
                                .build(),
                        ),
                        contentDescription = stringResource(R.string.post_image),
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(MediumSpace))
            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = descriptionState.text,
                hint = stringResource(id = R.string.description),
                singleLine = false,
                minLines = 3,
                maxLines = 7,
                maxLength = PostConstants.MAX_POST_DESCRIPTION_LENGTH,
                leadingIcon = (Icons.Default.PostAdd),
                error = when(descriptionState.error){
                     PostDescriptionError.FieldEmpty ->
                        stringResource(R.string.this_field_cant_be_empty)
                    else -> ""
                },
                onValueChange = {
                    viewModel.onEvent(
                        CreatePostEvent.EnterDescription(it)
                    )
                }
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            IconButton(
                onClick = {
                    viewModel.onEvent(CreatePostEvent.PostImage)
                },
                enabled = !viewModel.isLoading.value,
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

                Spacer(modifier = Modifier.width(SmallSpace))
                if(viewModel.isLoading.value){
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(20.dp),
                        strokeWidth = 2.dp
                    )
                }else{
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Send,
                        contentDescription = null
                    )
                }
            }
        }
    }
}