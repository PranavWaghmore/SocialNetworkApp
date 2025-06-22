package eu.tutorials.socialnetwork.presentaion.activity

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.presentaion.components.StandardScaffold
import io.ktor.websocket.Frame.Text

@Composable
fun ActivityScreen(
    navController: NavController
){
    StandardScaffold(
        modifier = Modifier,
        navController = navController
    ) {
        Text("ActivityScreen")
    }
}