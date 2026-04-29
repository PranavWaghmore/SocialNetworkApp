package pw.coding.konnecto.core.util

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import pw.coding.konnecto.feature_activity.presentation.ActivityScreen
import pw.coding.konnecto.feature_auth.presentation.login.LoginScreen
import pw.coding.konnecto.feature_auth.presentation.register.RegisterScreen
import pw.coding.konnecto.feature_auth.presentation.splash.SplashScreen
import pw.coding.konnecto.feature_chat.presentation.chat.ChatScreen
import pw.coding.konnecto.feature_chat.presentation.message.MessageScreen
import pw.coding.konnecto.feature_post.presentation.create_post.CreatePostScreen
import pw.coding.konnecto.feature_post.presentation.main_feed.MainFeedScreen
import pw.coding.konnecto.feature_post.presentation.personlist.PersonListScreen
import pw.coding.konnecto.feature_post.presentation.post_detail.PostDetailScreen
import pw.coding.konnecto.feature_profile.presentation.editProfileScreen.EditProfileScreen
import pw.coding.konnecto.feature_profile.presentation.profile.ProfileScreen
import pw.coding.konnecto.feature_profile.presentation.search.SearchScreen


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
        composable(
            route = Screen.MessagesScreen.route + "/{remoteUserId}/{remoteUsername}/{remoteUserProfilePictureUrl}?chatId={chatId}",
            arguments = listOf(
                navArgument("chatId") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("remoteUserId") {
                    type = NavType.StringType
                },
                navArgument("remoteUsername") {
                    type = NavType.StringType
                },
                navArgument("remoteUserProfilePictureUrl") {
                    type = NavType.StringType
                }
            )
        ) {
            val remoteUserId = it.arguments?.getString("remoteUserId")!!
            val remoteUsername = it.arguments?.getString("remoteUsername")!!
            val remoteUserProfilePictureUrl = it.arguments?.getString("remoteUserProfilePictureUrl")!!
            MessageScreen(
                remoteUserId = remoteUserId,
                remoteUsername = remoteUsername,
                encodedRemoteUserProfilePictureUrl = remoteUserProfilePictureUrl,
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
                navArgument(name = "userId") {
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
                onLogout = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
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
        composable(
            route = Screen.PostDetailScreen.route + "/{postId}?shouldShowKeyboard={shouldShowKeyboard}",
            arguments = listOf(
                navArgument(name = "postId") {
                    type = NavType.StringType
                },
                navArgument(name = "shouldShowKeyboard") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_VIEW
                    uriPattern = "konnecto://post/{postId}"
                }
            )
        ) {
            val shouldShowKeyboard = it.arguments?.getBoolean("shouldShowKeyboard") ?: false
            PostDetailScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                snackBarHostState = snackBarHostState,
                shouldShowKeyBoard = shouldShowKeyboard
            )
        }
        composable(
            Screen.EditProfileScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            EditProfileScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                snackBarHostState = snackBarHostState
            )
        }
        composable(
            route = Screen.PersonListScreen.route + "/{parentId}",
            arguments = listOf(
                navArgument(name = "parentId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            PersonListScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                snackBarHostState = snackBarHostState
            )
        }
    }
}