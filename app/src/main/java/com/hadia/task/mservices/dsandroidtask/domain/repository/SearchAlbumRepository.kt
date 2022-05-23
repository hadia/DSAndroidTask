package com.hadia.task.mservices.dsandroidtask.domain.repository

import com.hadia.task.mservices.dsandroidtask.data.model.remote.network.AlbumsDataSource
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import javax.inject.Inject

interface SearchAlbumRepository {
    suspend fun searchAlbums(query: String): List<Album>
}

class SearchAlbumRepositoryImpl @Inject constructor(private val dataSource: AlbumsDataSource) : SearchAlbumRepository {
    override suspend fun searchAlbums(query: String): List<Album> {
        // To simulate empty result
        if (query.length > 6) {
            return emptyList()
        }
        return dataSource.searchAlbums().shuffled()
    }
}
