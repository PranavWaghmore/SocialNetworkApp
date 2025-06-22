package eu.tutorials.socialnetwork.presentaion.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.presentaion.components.StandardScaffold

@Composable
fun ChatScreen(
    navController: NavController,
){
    StandardScaffold(
        navController = navController,
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text("Chat Screen" ,
                color = MaterialTheme.colorScheme.primary)
        }
    }
}