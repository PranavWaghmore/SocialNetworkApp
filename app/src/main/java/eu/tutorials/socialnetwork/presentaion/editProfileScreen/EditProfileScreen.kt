package eu.tutorials.socialnetwork.presentaion.editProfileScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.presentaion.components.StandardScaffold

@Composable
fun EditProfileScreen(
    navController: NavController
){
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text("Edit Profile Screen" ,
            color = Color.Green)
    }
}