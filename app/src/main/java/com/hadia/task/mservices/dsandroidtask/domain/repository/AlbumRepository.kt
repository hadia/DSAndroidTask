package com.hadia.task.mservices.dsandroidtask.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hadia.task.mservices.dsandroidtask.data.model.remote.AlbumsDataSource
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import kotlinx.coroutines.delay
import javax.inject.Inject

interface AlbumRepository

class AlbumRepositoryImpl @Inject constructor(private val dataSource: AlbumsDataSource) : AlbumRepository, PagingSource<Int, Album>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
        return try {
            val nextPage = params.key ?: 1
            val albums = dataSource.getAlbums()
            delay(1000)
            LoadResult.Page(
                data = albums,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < 6) nextPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Album>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
