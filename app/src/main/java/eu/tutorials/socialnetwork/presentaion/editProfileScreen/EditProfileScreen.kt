package eu.tutorials.socialnetwork.presentaion.editProfileScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.presentaion.components.StandardScaffold

@Composable
fun EditProfileScreen(
    navController: NavController
){
    StandardScaffold(
        modifier = Modifier,
        navController = navController
    ) {
        Text("EditProfileScreen")
    }
}