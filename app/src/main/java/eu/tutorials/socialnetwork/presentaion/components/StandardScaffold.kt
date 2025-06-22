package eu.tutorials.socialnetwork.presentaion.components

 import android.annotation.SuppressLint
 import androidx.compose.foundation.layout.Column
 import androidx.compose.foundation.layout.fillMaxWidth
 import androidx.compose.material.Divider
 import androidx.compose.material.icons.Icons
 import androidx.compose.material.icons.outlined.Add
 import androidx.compose.material.icons.outlined.Home
 import androidx.compose.material.icons.outlined.Message
 import androidx.compose.material.icons.outlined.Notifications
 import androidx.compose.material.icons.outlined.Person
 import androidx.compose.material3.BottomAppBar
 import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.Scaffold
 import androidx.compose.runtime.Composable
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.res.stringResource
 import androidx.compose.ui.unit.dp
 import androidx.hilt.navigation.compose.hiltViewModel
 import androidx.navigation.NavController
 import eu.tutorials.socialnetwork.R
 import eu.tutorials.socialnetwork.domain.models.BottomNavItem
 import eu.tutorials.socialnetwork.presentaion.ui.theme.DarkGrey
 import eu.tutorials.socialnetwork.presentaion.ui.theme.LightGray
 import eu.tutorials.socialnetwork.presentaion.util.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier= Modifier,
    viewModel: StandardScaffoldViewModel= hiltViewModel(),
    bottomNavItems : List<BottomNavItem> =listOf(
        BottomNavItem(
            route = Screen.MainFeedScreen.route,
            icon = Icons.Outlined.Home,
            contentDescription = stringResource(R.string.home),
            alertCount = null,
        ),
        BottomNavItem(
            route = Screen.ChatScreen.route,
            icon = Icons.Outlined.Message,
            contentDescription = stringResource(R.string.home),
            alertCount = 5,
        ),
        BottomNavItem(
            route = Screen.MainFeedScreen.route,
            icon = Icons.Outlined.Add,
            contentDescription = stringResource(R.string.chat)
        ),
        BottomNavItem(
            route = Screen.ActivityScreen.route,
            icon = Icons.Outlined.Notifications,
            contentDescription = stringResource(R.string.add),
            alertCount = null,
        ),
        BottomNavItem(
            route = Screen.ProfileScreen.route,
            icon = Icons.Outlined.Person,
            contentDescription = stringResource(R.string.home),
            alertCount = null,
        )

    ),
    content:@Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            Column {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = LightGray,
                    thickness = 1 .dp
                )
                BottomAppBar(
                    containerColor = DarkGrey,
                    tonalElevation = 8.dp,
                ) {
                    //BottomAppBar Icons here like home,chat
                    bottomNavItems.forEachIndexed { i, bottomNavItem ->
                        StandardBottomNavItem(
                            icon = bottomNavItem.icon,
                            contentDescription = bottomNavItem.contentDescription,
                            selected =
                                bottomNavItem.route == navController.currentDestination?.route,
                            alertCount = bottomNavItem.alertCount
                        ) {
                            navController.navigate(bottomNavItem.route)
                        }
                    }
                }
            }
        },
        modifier = Modifier
    ) {
       content()
    }
}