package pw.coding.konnecto.presentaion.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import pw.coding.konnecto.R
import pw.coding.konnecto.presentaion.util.Screen
import pw.coding.konnecto.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


@Composable
fun SplashScreen(
    navController: NavController ,
    dispatcher: CoroutineDispatcher = Dispatchers.Main
){
    // Animation state
    val scale = remember {
        Animatable(0f)
    }
    val overshootInterpolator=remember {
        OvershootInterpolator(2f)
    }

    LaunchedEffect(key1=true) {
        withContext(dispatcher) {
            scale.animateTo(
                targetValue = 0.5f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = {
                        overshootInterpolator.getInterpolation(it)
                    }
                )
            )
            delay(Constants.SPLASH_SCREEN_DURATION)
            navController.popBackStack()
            navController.navigate(Screen.LoginScreen.route)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize() ,
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id =R.drawable.logo),
            contentDescription = "logo",
        )
    }
}