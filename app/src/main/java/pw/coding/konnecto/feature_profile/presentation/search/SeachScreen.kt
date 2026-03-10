package pw.coding.konnecto.feature_profile.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PersonRemoveAlt1
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.User
import pw.coding.konnecto.core.presentation.components.StandardTextField
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.components.UserProfileItem
import pw.coding.konnecto.core.presentation.ui.theme.IconSizeMedium
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.util.Screen

@Composable
fun SearchScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.searchState.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StandardToolBar(
                onNavigateUp = onNavigateUp,
                showBackArrow = true,
                title = {
                    Text(
                        text = stringResource(R.string.search_for_users)
                    )
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(LargeSpace)
            ) {
                StandardTextField(
                    text = viewModel.searchFieldState.value.text,
                    hint = stringResource(id = R.string.search),
                    leadingIcon = Icons.Default.Search,
                    onValueChange = {
                        viewModel.onEvent(
                            event = SearchEvent.Query(it)
                        )
                    },
                )
                Spacer(modifier = Modifier.height(LargeSpace))
                LazyColumn {
                    items(state.userItems) { userItem ->
                        UserProfileItem(
                            user = User(
                                userId = userItem.username,
                                profilePictureUrl = userItem.profilePictureUrl,
                                username = userItem.username,
                                description = userItem.bio
                            ),
                            actionIcon = {
                                IconButton(
                                    onClick = {
                                        viewModel.onEvent(SearchEvent.ToggleFollowState(userItem.userId))
                                    }
                                ){
                                    Icon(
                                        imageVector = if(userItem.isFollowing){
                                            Icons.Default.PersonRemoveAlt1
                                        }else{
                                            Icons.Default.PersonAddAlt1
                                        },
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.size(IconSizeMedium)
                                    )
                                }
                            },
                            onItemClick = {
                                onNavigate(
                                    Screen.ProfileScreen.route + "?userId=${userItem.userId}"
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(MediumSpace))
                    }
                }
            }
        }
    }
}