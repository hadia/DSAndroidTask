package com.hadia.task.mservices.dsandroidtask.di

import com.hadia.task.mservices.dsandroidtask.data.model.remote.AlbumsDataSource
import com.hadia.task.mservices.dsandroidtask.domain.repository.AlbumRepository
import com.hadia.task.mservices.dsandroidtask.domain.repository.AlbumRepositoryImpl
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
}
