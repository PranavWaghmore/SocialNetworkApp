package eu.tutorials.socialnetwork.presentaion.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.tutorials.socialnetwork.domain.models.Post
import eu.tutorials.socialnetwork.presentaion.activity.ActivityScreen
import eu.tutorials.socialnetwork.presentaion.chat.ChatScreen
import eu.tutorials.socialnetwork.presentaion.create_post.CreatePostScreen
import eu.tutorials.socialnetwork.presentaion.editProfileScreen.EditProfileScreen
import eu.tutorials.socialnetwork.presentaion.login.LoginScreen
import eu.tutorials.socialnetwork.presentaion.main_feed.MainFeedScreen
import eu.tutorials.socialnetwork.presentaion.post_detail.PostDetailScreen
import eu.tutorials.socialnetwork.presentaion.profile.ProfileScreen
import eu.tutorials.socialnetwork.presentaion.register.RegisterScreen
import eu.tutorials.socialnetwork.presentaion.search.SearchScreen
import eu.tutorials.socialnetwork.presentaion.splash.SplashScreen


@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CreatePostScreen.route,
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
    }
}