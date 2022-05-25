package com.hadia.task.mservices.dsandroidtask.data.remote.network

import com.hadia.task.mservices.dsandroidtask.data.model.AlbumsAPIModel
import javax.inject.Inject

interface AlbumsDataSource {
    suspend fun getAlbums(): AlbumsAPIModel
    suspend fun searchAlbums(): AlbumsAPIModel
}

class AlbumsDataSourceImpl @Inject constructor(private val albumsService: AlbumsService) :
    AlbumsDataSource {

    override suspend fun getAlbums(): AlbumsAPIModel {
        return albumsService.getAlbums()
    }

    override suspend fun searchAlbums(): AlbumsAPIModel {
        return albumsService.searchAlbums()
    }
}
