package pw.coding.konnecto.feature_profile.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pw.coding.konnecto.R
import pw.coding.konnecto.core.domain.models.User
import pw.coding.konnecto.core.presentation.components.StandardTextField
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.components.UserProfileItem
import pw.coding.konnecto.core.presentation.ui.theme.IconSizeMedium
import pw.coding.konnecto.core.presentation.ui.theme.LargeSpace
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.core.util.state.StandardTextFieldState

@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            navController = navController,
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
                text = searchViewModel.searchState.value.text,
                hint = stringResource(id = R.string.search),
                error = searchViewModel.searchState.value.error,
                leadingIcon = Icons.Default.Search,
                onValueChange = {
                    searchViewModel.setSearchState(
                        StandardTextFieldState(it)
                    )
                },
            )
            Spacer(modifier = Modifier.height(LargeSpace))
            LazyColumn {
                items(10){
                    UserProfileItem(
                        user = User(
                            profilePictureUrl = "",
                            username = "Pranav Waghmore",
                            description = "Passionate Android developer crafting intuitive and" +
                                    " modern mobile experiences."
                        ),
                        actionIcon = {
                            Icon(
                                imageVector = Icons.Default.PersonAddAlt1,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(IconSizeMedium)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(MediumSpace))
                }
            }
        }
    }
}