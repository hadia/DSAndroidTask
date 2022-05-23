package com.hadia.task.mservices.dsandroidtask.domain.useCase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.domain.repository.AlbumRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbums @Inject constructor(
    private val repository: AlbumRepositoryImpl
) {
    operator fun invoke(): Flow<PagingData<Album>> {
        return Pager(PagingConfig(pageSize = 5)) {
            repository
        }.flow
    }
}
