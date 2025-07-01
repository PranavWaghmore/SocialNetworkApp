package eu.tutorials.socialnetwork.presentaion.create_post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.socialnetwork.presentaion.util.state.StandardTextFieldState
import javax.inject.Inject

@HiltViewModel()
class CreatePostViewModel @Inject constructor() : ViewModel(){

    private val _descriptionState = mutableStateOf(StandardTextFieldState())
    val descriptionState: State<StandardTextFieldState> = _descriptionState

    fun setDescriptionState(state: StandardTextFieldState) {
        _descriptionState.value = state
    }
}