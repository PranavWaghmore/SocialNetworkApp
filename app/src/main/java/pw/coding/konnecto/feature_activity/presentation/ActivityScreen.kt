package pw.coding.konnecto.feature_activity.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import pw.coding.konnecto.core.domain.models.Activity
import pw.coding.konnecto.core.presentation.components.StandardToolBar
import pw.coding.konnecto.core.presentation.ui.theme.DarkGrey
import pw.coding.konnecto.core.presentation.ui.theme.MediumSpace
import pw.coding.konnecto.feature_activity.presentation.components.ActivityItem

@Composable
fun ActivityScreen(
    viewModel: ActivityViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {
    val pagingState = viewModel.pagingState.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(pagingState.isLoading){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StandardToolBar(
                onNavigateUp = onNavigateUp,
                title = {
                    Text(
                        text = "Activity Screen",
                        color = Color.White
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = true
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkGrey),
                contentPadding = PaddingValues(MediumSpace)
            ) {
                items(pagingState.items.size) { i ->
                    val activity = pagingState.items[i]
                    if (i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                        viewModel.loadNextItems()
                    }
                    ActivityItem(
                        modifier = Modifier,
                        activity = activity,
                        onNavigate = onNavigate
                    )
                    Spacer(modifier = Modifier.height(MediumSpace))
                }
            }
        }
    }
}