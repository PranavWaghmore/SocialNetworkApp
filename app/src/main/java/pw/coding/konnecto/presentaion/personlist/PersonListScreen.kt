package pw.coding.konnecto.presentaion.personlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import pw.coding.konnecto.R
import pw.coding.konnecto.domain.models.User
import pw.coding.konnecto.presentaion.components.StandardToolBar
import pw.coding.konnecto.presentaion.components.UserProfileItem
import pw.coding.konnecto.presentaion.ui.theme.IconSizeMedium
import pw.coding.konnecto.presentaion.ui.theme.MediumSpace

@Composable
fun PersonListScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            navController = navController,
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
            items(15) {
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
