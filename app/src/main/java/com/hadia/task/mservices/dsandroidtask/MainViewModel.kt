package com.hadia.task.mservices.dsandroidtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.domain.useCase.GetAlbums
import com.hadia.task.mservices.dsandroidtask.domain.useCase.SearchAlbums
import com.hadia.task.mservices.dsandroidtask.ui.AlbumsSearchModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbums,
    private val searchAlbumsUseCase: SearchAlbums
) : ViewModel() {
    private val debouncePeriod: Long = 2000
    private val allAlbums = getAlbumsUseCase().cachedIn(viewModelScope)
    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    private val _loadedAlbums = MutableStateFlow(allAlbums)
    private val _matchedAlbums = MutableStateFlow(emptyFlow<PagingData<Album>>())
    private var _showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _isSearching: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _isSearchingListEmpty: MutableStateFlow<Boolean> = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val albums: StateFlow<Flow<PagingData<Album>>> =
        _searchText
            .flatMapLatest { search -> albums(search) }
            .stateInViewModel(initialValue = allAlbums)

    private fun albums(search: String?) = when {
        search.isNullOrEmpty() -> _loadedAlbums
        else -> _matchedAlbums
    }

    val albumsSearchModelState = combine(
        _searchText,
        _showProgressBar,
        _isSearching,
        _isSearchingListEmpty
    ) { text, showProgress, isSearchIng, isSearchingListEmpty ->
        AlbumsSearchModelState(
            text,
            showProgress,
            isSearchIng,
            isSearchingListEmpty
        )
    }

    fun onSearchTextChanged(changedSearchText: String) {
        _searchText.value = changedSearchText
        _isSearching.value = true
        if (changedSearchText.isEmpty()) {
            return
        }
        viewModelScope.launch {
            _showProgressBar.value = true

            changedSearchText.let {
                delay(debouncePeriod)
                val result = searchAlbumsUseCase.invoke(changedSearchText)
                _isSearchingListEmpty.value = result.first

                _matchedAlbums.value = result.second
            }

            _showProgressBar.value = false
        }
    }

    private fun <T> Flow<T>.stateInViewModel(initialValue: T): StateFlow<T> =
        stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = initialValue)

    fun onClearClick() {
        _searchText.value = ""
        _matchedAlbums.value = emptyFlow()
        _showProgressBar.value = false
        _isSearching.value = false
    }
}
