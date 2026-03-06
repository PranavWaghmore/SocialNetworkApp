package pw.coding.konnecto.core.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pw.coding.konnecto.core.domain.models.Post
import pw.coding.konnecto.feature_activity.presentation.ActivityScreen
import pw.coding.konnecto.feature_chat.chat.ChatScreen
import pw.coding.konnecto.feature_post.presentation.create_post.CreatePostScreen
import pw.coding.konnecto.feature_profile.presentation.editProfileScreen.EditProfileScreen
import pw.coding.konnecto.feature_auth.presentation.login.LoginScreen
import pw.coding.konnecto.feature_post.presentation.main_feed.MainFeedScreen
import pw.coding.konnecto.feature_post.presentation.personlist.PersonListScreen
import pw.coding.konnecto.feature_post.presentation.post_detail.PostDetailScreen
import pw.coding.konnecto.feature_profile.presentation.profile.ProfileScreen
import pw.coding.konnecto.feature_auth.presentation.register.RegisterScreen
import pw.coding.konnecto.feature_profile.presentation.search.SearchScreen
import pw.coding.konnecto.feature_auth.presentation.splash.SplashScreen


@Composable
fun Navigation(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = Modifier.fillMaxSize()
    )
    {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                onPopBackStack = navController::popBackStack,
                onNavigate = navController::navigate,
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onNavigate = navController::navigate,
                snackBarHostState = snackBarHostState
            )
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController, snackBarHostState = snackBarHostState)
        }
        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                snackBarHostState = snackBarHostState
            )
        }
        composable(Screen.ChatScreen.route) {
            ChatScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp
            )
        }
        composable(Screen.ActivityScreen.route) {
            ActivityScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp
            )
        }
        composable(
            route = Screen.ProfileScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument(name = "userId"){
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            ProfileScreen(
                userId = it.arguments?.getString("userId"),
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                snackBarHostState = snackBarHostState
            )
        }
        composable(Screen.CreatePostScreen.route) {
            CreatePostScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                snackBarHostState = snackBarHostState
            )
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp
            )
        }
        composable(Screen.PostDetailScreen.route) {
            PostDetailScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                post = Post(
                    username = "Pranav Waghmore",
                    imageUrl = "",
                    description = "Not just another post — it's a reflection of moments, memories, and milestones." +
                            "Every image holds a story, and this one is a piece of my journey.",
                    likeCount = 17,
                    commentCount = 7,
                    userId = "",
                    profilePictureUrl = "",
                    isLiked = true,
                    isOwnPost = true,
                    id = "",
                )
            )
        }
        composable(
            Screen.EditProfileScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId"){
                    type = NavType.StringType
                }
            )
        ) {
            EditProfileScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                snackBarHostState = snackBarHostState
            )
        }
        composable(Screen.PersonListScreen.route) {
            PersonListScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp
            )
        }
    }
}