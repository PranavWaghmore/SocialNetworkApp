package eu.tutorials.socialnetwork.presentaion.search

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.domain.models.User
import eu.tutorials.socialnetwork.presentaion.components.StandardTextField
import eu.tutorials.socialnetwork.presentaion.components.StandardToolBar
import eu.tutorials.socialnetwork.presentaion.components.UserProfileItem
import eu.tutorials.socialnetwork.presentaion.ui.theme.IconSizeMedium
import eu.tutorials.socialnetwork.presentaion.ui.theme.LargeSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.MediumSpace
import eu.tutorials.socialnetwork.presentaion.util.state.StandardTextFieldState

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