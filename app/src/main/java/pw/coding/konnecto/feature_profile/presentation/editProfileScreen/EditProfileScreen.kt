package pw.coding.konnecto.feature_profile.presentation.editProfileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.state.StandardTextFieldState
import pw.coding.konnecto.core.presentation.components.StandardTextField
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.presentation.ui.theme.ProfilePictureDpSizeLarge
import pw.coding.konnecto.feature_profile.presentation.editProfileScreen.components.Chip
import pw.coding.konnecto.feature_profile.presentation.util.EditProfileError
import kotlin.random.Random

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        StandardToolBar(
            navController = navController,
            showBackArrow = true,
            title = {
                Text("Edit your profile")
            },
            navActions = {
                IconButton(
                    onClick = {}
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
                bannerImage = painterResource(R.drawable.channelart),
                profileImage = painterResource((R.drawable.pranav)),
                profilePictureSize = ProfilePictureDpSizeLarge
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
                    error = when(viewModel.usernameState.value.error){
                        EditProfileError.FieldEmpty -> stringResource(R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    leadingIcon = Icons.Default.Person,
                    onValueChange = {
                        viewModel.setUsernameState(
                            StandardTextFieldState(text = it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(MediumSpace))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.githubTextFieldTextState.value.text,
                    hint = stringResource(id = R.string.github_profile_url),
                    error = when(viewModel.githubTextFieldTextState.value.error){
                        EditProfileError.FieldEmpty -> stringResource(R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(R.drawable.github_icon_1),
                    onValueChange = {
                        viewModel.setGithubTextFieldState(
                            StandardTextFieldState(text = it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(MediumSpace))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.instagramTextFieldState.value.text,
                    hint = stringResource(id = R.string.instagram_profile_url),
                    error = when(viewModel.instagramTextFieldState.value.error){
                        EditProfileError.FieldEmpty -> stringResource(R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(R.drawable.instagram_2016_5),
                    onValueChange = {
                        viewModel.steInstagramTextFieldState(
                            StandardTextFieldState(text = it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(MediumSpace))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.LinkedinTextFieldState.value.text,
                    hint = stringResource(id = R.string.linkedin_profile_url),
                    error = when(viewModel.LinkedinTextFieldState.value.error){
                        EditProfileError.FieldEmpty -> stringResource(R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(R.drawable.linkedin_icon_1),
                    onValueChange = {
                        viewModel.setLeetcodeTExtFieldState(
                            StandardTextFieldState(text = it)
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
                    error = when(viewModel.bioState.value.error){
                        EditProfileError.FieldEmpty -> stringResource(R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(R.drawable.bio_description),
                    onValueChange = {
                        viewModel.setBioState(
                            StandardTextFieldState(text = it)
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
                        listOf(
                            "Kotlin",
                            "Java",
                            "Python",
                            "Javascript",
                            "Dart"
                        ).forEach {
                            Chip(
                                text = it,
                                selected = Random.nextInt(2) == 0
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
    bannerImage: Painter,
    profileImage: Painter,
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
        Image(
            painter = bannerImage,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Image(
            painter = profileImage,
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
        )
    }
}