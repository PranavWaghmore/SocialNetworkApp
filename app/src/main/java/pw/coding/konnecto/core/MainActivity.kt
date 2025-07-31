package pw.coding.konnecto.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pw.coding.konnecto.core.presentation.components.StandardScaffold
import pw.coding.konnecto.core.presentation.ui.theme.SocialNetworkTheme
import pw.coding.konnecto.core.util.Navigation
import pw.coding.konnecto.core.util.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialNetworkTheme {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.Companion.fillMaxSize()
                )
                {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    val snackbarHostState = remember { SnackbarHostState() }
                    StandardScaffold(
                        navController = navController,
                        showBottomBar = currentRoute in listOf(
                            Screen.MainFeedScreen.route,
                            Screen.ChatScreen.route,
                            Screen.ActivityScreen.route,
                            Screen.ProfileScreen.route
                        ),
                        snackbarHostState = snackbarHostState,
                        modifier = Modifier.Companion.fillMaxSize()
                    ) {
                        Navigation(navController, snackbarHostState)
                    }
                }
            }
        }
    }
}