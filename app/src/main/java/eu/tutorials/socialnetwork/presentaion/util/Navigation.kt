package eu.tutorials.socialnetwork.presentaion.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.socialnetwork.presentaion.main_feed.MainFeedScreen
import eu.tutorials.socialnetwork.presentaion.login.LoginScreen
import eu.tutorials.socialnetwork.presentaion.register.RegisterScreen
import eu.tutorials.socialnetwork.presentaion.splash.SplashScreen


@Composable
fun Navigation(){
    val navController= rememberNavController()
    NavHost(
        navController = navController ,
        startDestination = Screen.SplashScreen.route
    )
    {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController=navController)
        }
        composable (Screen.LoginScreen.route){
           LoginScreen(navController=navController)
        }
        composable (Screen.RegisterScreen.route){
            RegisterScreen(navController=navController)
        }
        composable(Screen.MainFeedScreen.route){
            MainFeedScreen(navController=navController)
        }
    }
}