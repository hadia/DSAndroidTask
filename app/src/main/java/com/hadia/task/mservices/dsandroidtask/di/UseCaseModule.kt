package com.hadia.task.mservices.dsandroidtask.di

import com.hadia.task.mservices.dsandroidtask.domain.repository.AlbumRepositoryImpl
import com.hadia.task.mservices.dsandroidtask.domain.repository.SearchAlbumRepository
import com.hadia.task.mservices.dsandroidtask.domain.useCase.GetAlbums
import com.hadia.task.mservices.dsandroidtask.domain.useCase.SearchAlbums
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetAlbums(repository: AlbumRepositoryImpl): GetAlbums = GetAlbums(repository)

    @Provides
    fun provideSearchAlbums(repository: SearchAlbumRepository): SearchAlbums = SearchAlbums(repository)
}
