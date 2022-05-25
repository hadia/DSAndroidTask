package com.hadia.task.mservices.dsandroidtask.domain.repository

import com.hadia.task.mservices.dsandroidtask.data.model.toAlbum
import com.hadia.task.mservices.dsandroidtask.data.remote.network.AlbumsDataSource
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import javax.inject.Inject

interface SearchAlbumRepository {
    suspend fun searchAlbums(query: String): List<Album>
}

class SearchAlbumRepositoryImpl @Inject constructor(private val dataSource: AlbumsDataSource) : SearchAlbumRepository {
    override suspend fun searchAlbums(query: String): List<Album> {
        // To simulate empty result
        if (query.length > 5) {
            return emptyList()
        }
        return dataSource.searchAlbums().data?.sessions?.map { it.toAlbum() }?.shuffled() ?: emptyList()
    }
}
