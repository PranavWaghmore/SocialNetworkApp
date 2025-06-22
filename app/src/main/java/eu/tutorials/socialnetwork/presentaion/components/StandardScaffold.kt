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
 import androidx.compose.material3.Scaffold
 import androidx.compose.runtime.Composable
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.res.stringResource
 import androidx.compose.ui.unit.dp
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
    showBottomBar : Boolean = true,
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
            contentDescription = stringResource(R.string.chat),
            alertCount = 5,
        ),
        BottomNavItem(
            route = Screen.CreatePostScreen.route,
            icon = Icons.Outlined.Add,
            contentDescription = stringResource(R.string.make_post),
        ),
        BottomNavItem(
            route = Screen.ActivityScreen.route,
            icon = Icons.Outlined.Notifications,
            contentDescription = stringResource(R.string.notification),
            alertCount = 9,
        ),
        BottomNavItem(
            route = Screen.ProfileScreen.route,
            icon = Icons.Outlined.Person,
            contentDescription = stringResource(R.string.person),
            alertCount = null,
        )

    ),
    content:@Composable () -> Unit
) {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    Column {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = LightGray,
                            thickness = 1.dp
                        )
                        BottomAppBar(
                            containerColor = DarkGrey,
                            tonalElevation = 8.dp,
                        ) {
                            bottomNavItems.forEachIndexed { i, bottomNavItem ->
                                StandardBottomNavItem(
                                    icon = bottomNavItem.icon,
                                    contentDescription = bottomNavItem.contentDescription,
                                    selected =
                                        bottomNavItem.route == navController.currentDestination?.route,
                                    alertCount = bottomNavItem.alertCount
                                ) {
                                    if(navController.currentDestination?.route !=bottomNavItem.route){
                                        navController.navigate(bottomNavItem.route)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) {
            content()
        }
    }