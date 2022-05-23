package com.hadia.task.mservices.dsandroidtask.data.model.remote.network

import com.hadia.task.mservices.dsandroidtask.data.model.toAlbum
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import javax.inject.Inject

interface AlbumsDataSource {
    suspend fun getAlbums(): List<Album>
    suspend fun searchAlbums(): List<Album>
}

class AlbumsDataSourceImpl @Inject constructor(private val albumsService: AlbumsService) :
    AlbumsDataSource {

    override suspend fun getAlbums(): List<Album> {
        return albumsService.getAlbums().data?.sessions?.map { it.toAlbum() } ?: emptyList()
    }

    override suspend fun searchAlbums(): List<Album> {
        return albumsService.searchAlbums().data?.sessions?.map { it.toAlbum() } ?: emptyList()
    }
}
