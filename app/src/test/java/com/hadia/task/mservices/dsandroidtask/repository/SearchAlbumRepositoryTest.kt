package com.hadia.task.mservices.dsandroidtask.repository

import com.hadia.task.mservices.dsandroidtask.MainCoroutineScopeRule
import com.hadia.task.mservices.dsandroidtask.data.model.toAlbum
import com.hadia.task.mservices.dsandroidtask.data.remote.network.AlbumsDataSource
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.domain.model.DataProvider
import com.hadia.task.mservices.dsandroidtask.domain.repository.SearchAlbumRepository
import com.hadia.task.mservices.dsandroidtask.domain.repository.SearchAlbumRepositoryImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchAlbumRepositoryTest {
    private lateinit var dataSource: AlbumsDataSource
    private lateinit var repository: SearchAlbumRepository
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutinesRule = MainCoroutineScopeRule()

    @Before
    fun setup() {
        dataSource = mock()
        repository = SearchAlbumRepositoryImpl(dataSource)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when albums search list successfully`() = runTest {
        val list = DataProvider.list.data?.sessions?.map { it.toAlbum() } ?: emptyList()
        whenever(dataSource.searchAlbums()).thenReturn(DataProvider.list)

        val result = repository.searchAlbums("3dd")
        Assert.assertEquals(list, result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when albums search empty list successfully`() = runTest {
        val list = DataProvider.list.data?.sessions?.map { it.toAlbum() } ?: emptyList()
        whenever(dataSource.searchAlbums()).thenReturn(DataProvider.list)

        val result = repository.searchAlbums("3deeeed")
        Assert.assertEquals(emptyList<Album>(), result)
    }
}
