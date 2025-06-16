package eu.tutorials.socialnetwork.presentaion.login

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import eu.tutorials.socialnetwork.R
import eu.tutorials.socialnetwork.presentaion.components.StandardTextField
import eu.tutorials.socialnetwork.presentaion.ui.theme.MediumSpace
import eu.tutorials.socialnetwork.presentaion.ui.theme.MediumSpace


@Composable
fun LoginScreen(
    navController: NavController
){
    Column (
        verticalArrangement = Arrangement.Center ,
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = MediumSpace),
    ){
        Text(
            text= stringResource(id=R.string.login) ,
            color= MaterialTheme.colorScheme.onBackground ,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(MediumSpace))
        //StandardTextField(text = )
    }
}