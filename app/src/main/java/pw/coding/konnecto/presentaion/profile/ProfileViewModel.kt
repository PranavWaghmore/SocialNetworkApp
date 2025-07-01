package pw.coding.konnecto.presentaion.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pw.coding.konnecto.presentaion.profile.components.ProfileToolBarState
import javax.inject.Inject

@HiltViewModel
 class ProfileViewModel @Inject constructor(

 ) : ViewModel(){

     private val _toolbarState= mutableStateOf(ProfileToolBarState())
     val toolbarState : State<ProfileToolBarState> = _toolbarState

    fun setExpandedRatio( ratio: Float){
        _toolbarState.value = _toolbarState.value.copy(expandedRatio = ratio)
        println("UPDATING TOOLBAR STATE TO $toolbarState")
    }

     fun setToolbarOffset( value: Float){
         _toolbarState.value = _toolbarState.value.copy(toolbarOffsetY = value)
         println("UPDATING TOOLBAR STATE TO $toolbarState")
     }
 }