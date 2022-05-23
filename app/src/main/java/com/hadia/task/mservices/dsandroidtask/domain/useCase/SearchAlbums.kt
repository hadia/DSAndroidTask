package com.hadia.task.mservices.dsandroidtask.domain.useCase

import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.domain.repository.SearchAlbumRepository
import javax.inject.Inject

class SearchAlbums @Inject constructor(
    private val repository: SearchAlbumRepository
) {
    suspend operator fun invoke(query: String): List<Album> {
        return repository.searchAlbums(query)
    }
}
