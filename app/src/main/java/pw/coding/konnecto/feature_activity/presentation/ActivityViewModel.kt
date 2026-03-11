package pw.coding.konnecto.feature_activity.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import pw.coding.konnecto.feature_activity.domain.use_case.GetActivitiesUseCase
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivities: GetActivitiesUseCase
): ViewModel(){

    private val _state = mutableStateOf(ActivityState())
    val state : State<ActivityState> = _state

    val activities = getActivities().cachedIn(viewModelScope)

    fun onEvent(event : ActivityEvent){
        when(event){
            is ActivityEvent.ClickedOnUser -> {

            }
            is ActivityEvent.ClickedOnParent -> {

            }
        }
    }

}