package pw.coding.konnecto.feature_activity.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import pw.coding.konnecto.core.domain.models.Activity
import pw.coding.konnecto.core.presentation.PagingState
import pw.coding.konnecto.core.presentation.util.UiEvent
import pw.coding.konnecto.core.util.DefaultPaginator
import pw.coding.konnecto.feature_activity.domain.use_case.GetActivitiesUseCase
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
     private val getActivities: GetActivitiesUseCase
): ViewModel(){


    private val _pagingState = mutableStateOf<PagingState<Activity>>(PagingState())
    val pagingState: State<PagingState<Activity>> = _pagingState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(

        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            getActivities(page)
        },
        onSuccess = { activities ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + activities,
                isLoading = false,
                endReached = activities.isEmpty()
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
        }
    )

    init {
        loadNextItems()
    }

    fun onEvent(event : ActivityEvent){
        when(event){
            is ActivityEvent.ClickedOnUser -> {

            }
            is ActivityEvent.ClickedOnParent -> {

            }
        }
    }


    fun loadNextItems(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

}