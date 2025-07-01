package pw.coding.konnecto.presentaion.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pw.coding.konnecto.domain.models.Post
import pw.coding.konnecto.presentaion.activity.ActivityScreen
import pw.coding.konnecto.presentaion.chat.ChatScreen
import pw.coding.konnecto.presentaion.create_post.CreatePostScreen
import pw.coding.konnecto.presentaion.editProfileScreen.EditProfileScreen
import pw.coding.konnecto.presentaion.login.LoginScreen
import pw.coding.konnecto.presentaion.main_feed.MainFeedScreen
import pw.coding.konnecto.presentaion.personlist.PersonListScreen
import pw.coding.konnecto.presentaion.post_detail.PostDetailScreen
import pw.coding.konnecto.presentaion.profile.ProfileScreen
import pw.coding.konnecto.presentaion.register.RegisterScreen
import pw.coding.konnecto.presentaion.search.SearchScreen
import pw.coding.konnecto.presentaion.splash.SplashScreen


@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = Modifier.fillMaxSize()
    )
    {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(navController = navController)
        }
        composable(Screen.ChatScreen.route) {
            ChatScreen(navController = navController)
        }
        composable(Screen.ActivityScreen.route) {
            ActivityScreen(navController = navController)
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.CreatePostScreen.route) {
            CreatePostScreen(navController = navController)
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
        composable(Screen.PostDetailScreen.route) {
            PostDetailScreen(navController = navController,
                post = Post(
                        username = "Pranav Waghmore",
                        imageUrl = "",
                        postPictureUrl = "",
                        description = "Not just another post — it's a reflection of moments, memories, and milestones."+
                                      "Every image holds a story, and this one is a piece of my journey.",
                        likeCount = 17,
                        commentCount = 7
                    )
                )
        }
        composable(Screen.EditProfileScreen.route){
            EditProfileScreen(navController = navController)
        }
        composable(Screen.PersonListScreen.route){
            PersonListScreen(navController = navController)
        }
    }
}