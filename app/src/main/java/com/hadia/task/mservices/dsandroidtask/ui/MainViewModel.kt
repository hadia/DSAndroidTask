package com.hadia.task.mservices.dsandroidtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.domain.useCase.GetAlbums
import com.hadia.task.mservices.dsandroidtask.domain.useCase.SearchAlbums
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbums,
    private val searchAlbumsUseCase: SearchAlbums
) : ViewModel() {
    private val debouncePeriod: Long = 500

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")

    private val allAlbums = getAlbumsUseCase.invoke()
    private val _loadedAlbums = MutableStateFlow(allAlbums)
    private val _matchedAlbums = MutableStateFlow(emptyFlow<PagingData<Album>>())

    private var _showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _isSearching: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _isSearchingListEmpty: MutableStateFlow<Boolean> = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val albums: StateFlow<Flow<PagingData<Album>>> =
        _searchText
            .flatMapLatest { search -> albums(search) }
            .stateInViewModel(initialValue = _loadedAlbums.value)

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

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun onSearchTextChanged(changedSearchText: String) {
        _searchText.value = changedSearchText

        if (changedSearchText.isEmpty()) {
            return
        }

        _matchedAlbums.value = _searchText.debounce(debouncePeriod)
            .distinctUntilChanged()
            .flatMapLatest {
                searchAlbumsUseCase.invoke(
                    changedSearchText,
                    onStart = {
                        _isSearching.value = true
                        _showProgressBar.value = true
                    },
                    onComplete = { _showProgressBar.value = false },
                    isSearchingResultsEmpty = { _isSearchingListEmpty.value = it },
                    onError = { }
                )
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
