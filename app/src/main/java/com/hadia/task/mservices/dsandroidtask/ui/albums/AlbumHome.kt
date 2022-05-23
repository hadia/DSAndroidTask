/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hadia.task.mservices.dsandroidtask

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.ui.composables.ErrorItem
import com.hadia.task.mservices.dsandroidtask.ui.composables.LoadingItem
import com.hadia.task.mservices.dsandroidtask.ui.composables.LoadingView
import kotlinx.coroutines.flow.Flow

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
fun AlbumContent(albumList: Flow<PagingData<Album>>) {
    remember { albumList }.let { albums ->
        val lazyMovieItems: LazyPagingItems<Album> = albums.collectAsLazyPagingItems()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {
            items(
                lazyPagingItems = lazyMovieItems,
                itemContent = {
                    it?.let { it1 -> AlbumListItem(albumUIModel = it1) }
                }
            )

            lazyMovieItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingView(modifier = Modifier.fillMaxSize()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = lazyMovieItems.loadState.refresh as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage ?: stringResource(id = R.string.search),
                                modifier = Modifier.fillMaxSize(),
                                onClick = { retry() }
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = lazyMovieItems.loadState.append as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage ?: stringResource(id = R.string.search),
                                onClick = { retry() }
                            )
                        }
                    }
                }
            }
        }
    }
}
