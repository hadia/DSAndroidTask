package com.hadia.task.mservices.dsandroidtask.domain.useCase

import androidx.paging.PagingData
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.domain.repository.SearchAlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchAlbums @Inject constructor(
    private val repository: SearchAlbumRepository
) {
    suspend operator fun invoke(query: String): Pair<Boolean, Flow<PagingData<Album>>> {
        val searchList = repository.searchAlbums(query)
        return Pair(searchList.isEmpty(), flowOf(PagingData.from(searchList)))
    }
}
