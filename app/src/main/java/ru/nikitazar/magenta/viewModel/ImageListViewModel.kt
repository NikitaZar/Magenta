package ru.nikitazar.magenta.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.nikitazar.domain.models.Image
import ru.nikitazar.domain.useCases.DislikeUseCase
import ru.nikitazar.domain.useCases.GetFavoriteUseCase
import ru.nikitazar.domain.useCases.GetPageUseCase
import ru.nikitazar.domain.useCases.LikeUseCase
import javax.inject.Inject

private const val DEBOUNCE = 500L

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getPageUseCase: GetPageUseCase,
    private val likeUseCase: LikeUseCase,
    private val dislikeUseCase: DislikeUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : ViewModel() {

    private val _pageDataFlow = MutableStateFlow(Image())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pageDataFlow: Flow<PagingData<Image>> = _pageDataFlow.debounce(DEBOUNCE).flatMapLatest {
        getPageUseCase.execute()
    }.cachedIn(viewModelScope)

    fun like(id: Int) = viewModelScope.launch {
        likeUseCase.execute(id)
        getFavorite()
    }

    fun dislike(id: Int) = viewModelScope.launch {
        dislikeUseCase.execute(id)
        getFavorite()
    }

    val favoriteData: LiveData<List<Image>>
        get() = _favoriteData.asLiveData()
    private val _favoriteData = MutableStateFlow(emptyList<Image>())

    fun getFavorite() = viewModelScope.launch(Dispatchers.IO) {
        _favoriteData.emit(getFavoriteUseCase.execute())
    }
}