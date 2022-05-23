package com.hadia.task.mservices.dsandroidtask.ui

import com.hadia.task.mservices.dsandroidtask.domain.model.Album

data class AlbumsSearchModelState(
    val searchText: String = "",
    val albums: List<Album> = emptyList(),
    val showProgressBar: Boolean = false
) {
    companion object {
        val Empty = AlbumsSearchModelState()
    }
}
