package pw.coding.konnecto.feature_profile.presentation.editProfileScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import pw.coding.konnecto.R
import pw.coding.konnecto.core.presentation.components.StandardTextField
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.presentation.ui.theme.ProfilePictureDpSizeLarge
import pw.coding.konnecto.core.presentation.util.CropActivityResultContract
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.presentation.util.asString
import pw.coding.konnecto.feature_profile.presentation.editProfileScreen.components.Chip
import pw.coding.konnecto.feature_profile.presentation.util.EditProfileError

@Composable
fun EditProfileScreen(
    snackBarHostState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: EditProfileViewModel = hiltViewModel(),
) {

    val profileState = viewModel.profileState.value

    val profilePictureCropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(EditProfileEvent.CropProfileImage(uri = it))
    }
    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            profilePictureCropActivityLauncher.launch(it)
        }
    }

    val bannerImageCropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(5f, 2f)
    ) {
        viewModel.onEvent(EditProfileEvent.CropBannerImage(uri = it))
    }
    val bannerImageGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            bannerImageCropActivityLauncher.launch(it)
        }
    }

    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.NavigateUp ->{
                    onNavigateUp()
                }
                else -> {}
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        StandardToolBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(stringResource(R.string.edit_your_profile))
            },
            navActions = {
                IconButton(
                    onClick = {
                        viewModel.onEvent(EditProfileEvent.UpdateProfile)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.save_changes)
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            EditBannerSection(
                bannerImageUrl = viewModel.bannerUri.value?.toString()
                    ?: (profileState.profile?.bannerUrl ?: ""),
                profileImageUrl = viewModel.profilePictureUri.value?.toString()
                    ?: (profileState.profile?.profilePictureUrl ?: ""),
                profilePictureSize = ProfilePictureDpSizeLarge,
                onBannerClick = {
                    bannerImageGalleryLauncher.launch("image/*")
                },
                onProfilePictureClick = {
                    profilePictureGalleryLauncher.launch("image/*")
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LargeSpace)
                    .align(Alignment.CenterHorizontally)
            ) {
                Spacer(modifier = Modifier.height(MediumSpace))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.usernameState.value.text,
                    hint = stringResource(id = R.string.username),
                    error = when (viewModel.usernameState.value.error) {
                        EditProfileError.FieldEmpty -> stringResource(R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    leadingIcon = Icons.Default.Person,
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredUsername(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(MediumSpace))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.githubTextFieldTextState.value.text,
                    hint = stringResource(id = R.string.github_profile_url),
                    error = when (viewModel.githubTextFieldTextState.value.error) {
                        EditProfileError.FieldEmpty -> stringResource(R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(R.drawable.github_icon_1),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredGitHubUrl(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(MediumSpace))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.instagramTextFieldState.value.text,
                    hint = stringResource(id = R.string.instagram_profile_url),
                    error = when (viewModel.instagramTextFieldState.value.error) {
                        EditProfileError.FieldEmpty -> stringResource(R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(R.drawable.instagram_2016_5),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredInstagramUrl(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(MediumSpace))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.linkedinTextFieldState.value.text,
                    hint = stringResource(id = R.string.linkedin_profile_url),
                    error = when (viewModel.linkedinTextFieldState.value.error) {
                        EditProfileError.FieldEmpty -> stringResource(R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(R.drawable.linkedin_icon_1),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredLinkedInUrl(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(MediumSpace))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.bioState.value.text,
                    hint = stringResource(id = R.string.description),
                    singleLine = false,
                    minLines = 3,
                    maxLines = 3,
                    error = when (viewModel.bioState.value.error) {
                        EditProfileError.FieldEmpty -> stringResource(R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(R.drawable.bio_description),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredBio(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(LargeSpace))
                Text(
                    text = stringResource(R.string.select_top3_skills),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(MediumSpace))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LargeSpace)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        maxItemsInEachRow = 3
                    ) {
                        viewModel.skills.value.skills.forEach { skill ->
                            Chip(
                                text = skill.name,
                                selected = viewModel.skills.value.selectedSkills.any {
                                    it.name == skill.name
                                },
                                onChipClick = {
                                    viewModel.onEvent(
                                        EditProfileEvent.SetSkillSelected(skill)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun EditBannerSection(
    bannerImageUrl: String,
    profileImageUrl: String,
    profilePictureSize: Dp = ProfilePictureDpSizeLarge,
    onBannerClick: () -> Unit = {},
    onProfilePictureClick: () -> Unit = {}
) {
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight + profilePictureSize / 2f),
    ) {
        AsyncImage(
            model = bannerImageUrl,
            contentDescription = stringResource(R.string.banner),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(bannerHeight)
                .clickable { onBannerClick() }
        )
        AsyncImage(
            model = profileImageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(profilePictureSize)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = CircleShape
                )
                .align(Alignment.BottomCenter)
                .clickable { onProfilePictureClick() }
        )
    }
}