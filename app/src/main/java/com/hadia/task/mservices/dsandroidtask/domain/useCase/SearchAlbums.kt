package com.hadia.task.mservices.dsandroidtask.domain.useCase

import androidx.paging.PagingData
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.domain.repository.SearchAlbumRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchAlbums @Inject constructor(
    private val repository: SearchAlbumRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    operator fun invoke(
        query: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        isSearchingResultsEmpty: (Boolean) -> Unit,
        onError: (String?) -> Unit
    ): Flow<PagingData<Album>> {
        return flow {
            val searchList = repository.searchAlbums(query)
            isSearchingResultsEmpty(searchList.isEmpty())
            emit(PagingData.from(searchList))
        }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(coroutineDispatcher)
    }
}
