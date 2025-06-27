package eu.tutorials.socialnetwork.presentaion.profile

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.presentaion.util.Screen

@Composable
fun EditProfileScreen(
    navController: NavController
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = " Edit Profile Screen",
            textAlign = TextAlign.Center ,
            color = MaterialTheme.colorScheme.primary
        )
    }
}