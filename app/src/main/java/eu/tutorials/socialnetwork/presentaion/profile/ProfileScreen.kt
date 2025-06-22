package eu.tutorials.socialnetwork.presentaion.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.presentaion.components.StandardScaffold
import eu.tutorials.socialnetwork.presentaion.util.Screen

@Composable
fun ProfileScreen(
    navController: NavController
){
    StandardScaffold(
        modifier = Modifier,
        navController = navController
    ) {
        Text("Profile Screen")
    }
}