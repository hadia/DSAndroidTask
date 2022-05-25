package com.hadia.task.mservices.dsandroidtask.data.remote.network

import com.hadia.task.mservices.dsandroidtask.data.model.AlbumsAPIModel
import retrofit2.http.GET

interface AlbumsService {
    @GET("5df79a3a320000f0612e0115")
    suspend fun getAlbums(): AlbumsAPIModel

    @GET("5df79b1f320000f4612e011e")
    suspend fun searchAlbums(): AlbumsAPIModel
}
