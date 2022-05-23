package com.hadia.task.mservices.dsandroidtask.di

import com.hadia.task.mservices.dsandroidtask.data.model.remote.AlbumsDataSource
import com.hadia.task.mservices.dsandroidtask.data.model.remote.AlbumsDataSourceImpl
import com.hadia.task.mservices.dsandroidtask.data.model.remote.network.AlbumsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideAlbumsDataSource(
        albumsService: AlbumsService
    ): AlbumsDataSource = AlbumsDataSourceImpl(albumsService)
}
