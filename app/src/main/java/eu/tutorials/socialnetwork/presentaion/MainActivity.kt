package eu.tutorials.socialnetwork.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import eu.tutorials.socialnetwork.presentaion.ui.theme.SocialNetworkTheme
import eu.tutorials.socialnetwork.presentaion.util.Navigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()//staus bar??
        setContent {
            SocialNetworkTheme {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.Companion.fillMaxSize()
                )
                {
                    Navigation()
                }
            }
        }
    }
}