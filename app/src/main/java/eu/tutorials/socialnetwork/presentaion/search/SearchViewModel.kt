package eu.tutorials.socialnetwork.presentaion.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.socialnetwork.presentaion.util.state.StandardTextFieldState
import javax.inject.Inject

@HiltViewModel()
class SearchViewModel @Inject
constructor(): ViewModel() {

    private val _searchState = mutableStateOf(StandardTextFieldState())
    val searchState: State<StandardTextFieldState>  = _searchState

    fun setSearchState(state: StandardTextFieldState){
        _searchState.value = state
    }
}