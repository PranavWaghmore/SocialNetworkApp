package pw.coding.konnecto.feature_post.presentation.main_feed

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pw.coding.konnecto.feature_post.domain.use_case.PostUseCases
import javax.inject.Inject

@HiltViewModel()
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases
): ViewModel(){

}