package com.hadia.task.mservices.dsandroidtask.ui.albums

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hadia.task.mservices.dsandroidtask.R
import com.hadia.task.mservices.dsandroidtask.data.model.toAlbum
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.domain.model.DataProvider
import com.hadia.task.mservices.dsandroidtask.ui.composables.EmptyStateView
import com.hadia.task.mservices.dsandroidtask.ui.composables.ErrorItem
import com.hadia.task.mservices.dsandroidtask.ui.composables.LoadingItem
import com.hadia.task.mservices.dsandroidtask.ui.composables.LoadingView
import kotlinx.coroutines.flow.flowOf

@ExperimentalFoundationApi
fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable (value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) {
        itemContent(lazyPagingItems[it])
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumContent(
    lazyAlbumItems: LazyPagingItems<Album>,
    isSearchingListEmpty: Boolean = false,
    searchingList: Boolean = false
) {
    when {
        lazyAlbumItems.loadState.refresh is LoadState.NotLoading -> {
            Column(modifier = Modifier.fillMaxSize()) {
                if (lazyAlbumItems.itemCount > 0) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                alpha = if (lazyAlbumItems.itemCount > 0) 1f else 0f
                            }
                    ) {
                        items(
                            lazyPagingItems = lazyAlbumItems,
                            itemContent = {
                                it?.let { it1 -> AlbumListItem(albumUIModel = it1) }
                            }
                        )
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize()) {
                        EmptyStateView(
                            message = "No albums found",
                            isVisible = searchingList && isSearchingListEmpty
                        )

                        EmptyStateView(
                            message = "No Data found",
                            isVisible = !searchingList
                        )
                    }
                }
            }
        }
        lazyAlbumItems.loadState.prepend is LoadState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }
        lazyAlbumItems.loadState.refresh is LoadState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }
        lazyAlbumItems.loadState.append is LoadState.Loading -> {
            LoadingItem()
        }
        lazyAlbumItems.loadState.refresh is LoadState.Error -> {
            val e = lazyAlbumItems.loadState.refresh as LoadState.Error

            ErrorItem(
                message = e.error.localizedMessage
                    ?: stringResource(id = R.string.search),
                modifier = Modifier.fillMaxSize(),
                onClick = { lazyAlbumItems.retry() }
            )
        }
        lazyAlbumItems.loadState.append is LoadState.Error -> {
            val e = lazyAlbumItems.loadState.append as LoadState.Error

            ErrorItem(
                message = e.error.localizedMessage
                    ?: stringResource(id = R.string.search),
                onClick = { lazyAlbumItems.retry() }
            )
        }
    }
}

@Preview
@Composable
fun PreviewAlbumContent() {
    val list = DataProvider.list.data?.sessions?.map { it.toAlbum() } ?: emptyList()
    val albumUIModel = flowOf(PagingData.from(list))
    // AlbumContent(albumList = emptyFlow())
    AlbumContent(lazyAlbumItems = albumUIModel.collectAsLazyPagingItems())
}
