package pw.coding.konnecto.feature_post.presentation.personlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PersonRemoveAlt1
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.collectLatest
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.User
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.components.UserProfileItem
import pw.coding.konnecto.core.presentation.ui.theme.IconSizeMedium
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.presentation.util.asString
import pw.coding.konnecto.core.util.Screen

@Composable
fun PersonListScreen(
    viewModel: PersonListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    snackBarHostState: SnackbarHostState
) {
    val state = viewModel.state.value

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is UiEvent.ShowSnackbar ->{
                    snackBarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                else -> {}
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
           onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.like_by)
                )
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(MediumSpace)
        ) {
            items(state.users) { userItem ->
                UserProfileItem(
                    user = userItem,
                    actionIcon = {
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
                    },
                    onItemClick = {
                        onNavigate(Screen.ProfileScreen.route + "?userId=${userItem.userId}")
                    },
                    onActionItemClick = {
                        viewModel.onEvent(PersonListEvent.ToggleFollow(userItem.userId))
                    }
                )
                Spacer(modifier = Modifier.height(MediumSpace))
            }
        }
    }
}
