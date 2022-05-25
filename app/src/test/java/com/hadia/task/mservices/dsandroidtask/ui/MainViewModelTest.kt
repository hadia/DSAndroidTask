@file:OptIn(ExperimentalTime::class)

package com.hadia.task.mservices.dsandroidtask.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import app.cash.turbine.test
import com.hadia.task.mservices.dsandroidtask.MainCoroutineScopeRule
import com.hadia.task.mservices.dsandroidtask.collectDataForTest
import com.hadia.task.mservices.dsandroidtask.data.model.toAlbum
import com.hadia.task.mservices.dsandroidtask.domain.model.DataProvider
import com.hadia.task.mservices.dsandroidtask.domain.repository.SearchAlbumRepository
import com.hadia.task.mservices.dsandroidtask.domain.useCase.GetAlbums
import com.hadia.task.mservices.dsandroidtask.domain.useCase.SearchAlbums
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineScopeRule()

    private lateinit var viewModel: MainViewModel

    private val list = DataProvider.list.data?.sessions?.map { it.toAlbum() } ?: emptyList()
    private val pagingData = PagingData.from(list)
    private val flow = flowOf(pagingData)
    private val getAlbumsUseCase = mock<GetAlbums> {
        on { invoke() } doReturn flow
    }

    private val searchList = listOf(DataProvider.searchAlbum.toAlbum())
    private lateinit var searchAlbumsUseCase: SearchAlbums

    private val repository: SearchAlbumRepository = mock()
    @Before
    fun setUp() {

        searchAlbumsUseCase = SearchAlbums(
            repository, coroutineRule.dispatcher
        )

        viewModel = MainViewModel(getAlbumsUseCase, searchAlbumsUseCase)
    }

    @Test
    fun `when albums load shows list successfully`() = runBlocking() {
        getAlbumsUseCase.invoke().test(2.toDuration(DurationUnit.SECONDS)) {
            val item = awaitItem()
            Assert.assertEquals(list, item.collectDataForTest())
            awaitComplete()
        }
        Assert.assertEquals(pagingData, viewModel.albums.value.toList().first())
    }

    @Test
    fun `when albums search shows list successfully`() = runTest {

        whenever(repository.searchAlbums(any())).thenReturn(searchList)
        searchAlbumsUseCase.invoke(
            query = "test",
            onStart = {},
            onComplete = {},
            isSearchingResultsEmpty = { },
            onError = {}
        ).test(2.toDuration(DurationUnit.SECONDS)) {
            val item = awaitItem()
            Assert.assertEquals(searchList, item.collectDataForTest())
            awaitComplete()
        }
        viewModel.onSearchTextChanged("test")

        Assert.assertEquals(pagingData, viewModel.albums.value.toList().first())

        viewModel.albumsSearchModelState.test(3.toDuration(DurationUnit.SECONDS)) {
            val item = awaitItem()
            Assert.assertEquals("test", item.searchText)
            cancel()
        }
    }

    @Test
    fun `when albums search query isEmpty`() = runTest {
        viewModel.onSearchTextChanged("")

        Assert.assertEquals(pagingData, viewModel.albums.value.toList().first())

        viewModel.albumsSearchModelState.test(3.toDuration(DurationUnit.SECONDS)) {
            val item = awaitItem()
            Assert.assertEquals("", item.searchText)
            cancel()
        }
    }
}
