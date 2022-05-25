package com.hadia.task.mservices.dsandroidtask.di

import com.hadia.task.mservices.dsandroidtask.data.remote.network.AlbumsDataSource
import com.hadia.task.mservices.dsandroidtask.domain.repository.AlbumRepository
import com.hadia.task.mservices.dsandroidtask.domain.repository.AlbumRepositoryImpl
import com.hadia.task.mservices.dsandroidtask.domain.repository.SearchAlbumRepository
import com.hadia.task.mservices.dsandroidtask.domain.repository.SearchAlbumRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAlbumRepository(
        dataSource: AlbumsDataSource
    ): AlbumRepository = AlbumRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideSearchAlbumRepository(
        dataSource: AlbumsDataSource
    ): SearchAlbumRepository = SearchAlbumRepositoryImpl(dataSource)
}
