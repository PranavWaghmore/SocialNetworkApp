package pw.coding.konnecto.feature_profile.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.util.Resource
import pw.coding.konnecto.core.util.UiText
import pw.coding.konnecto.feature_profile.domain.use_case.ProfileUseCases
import pw.coding.konnecto.feature_profile.presentation.profile.components.ProfileToolBarState
import javax.inject.Inject

@HiltViewModel
 class ProfileViewModel @Inject constructor(
     private val profileUseCases: ProfileUseCases,
     savedStateHandle: SavedStateHandle
 ) : ViewModel(){

     private val _toolbarState= mutableStateOf(ProfileToolBarState())
     val toolbarState : State<ProfileToolBarState> = _toolbarState

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            getProfile(userId)
        }
    }
    fun setExpandedRatio( ratio: Float){
        _toolbarState.value = _toolbarState.value.copy(expandedRatio = ratio)
    }

     fun setToolbarOffset( value: Float){
         _toolbarState.value = _toolbarState.value.copy(toolbarOffsetY = value)
     }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getProfile(userId: String){
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            when(val result = profileUseCases.getProfile(userId)){
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        profile = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value= state.value.copy(isLoading = false)
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }
 }