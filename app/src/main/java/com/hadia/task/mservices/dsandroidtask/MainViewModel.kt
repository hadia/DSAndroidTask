package com.hadia.task.mservices.dsandroidtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.domain.useCase.GetAlbums
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbums
) : ViewModel() {
    val albums: Flow<PagingData<Album>> = getAlbumsUseCase().cachedIn(viewModelScope)
}
