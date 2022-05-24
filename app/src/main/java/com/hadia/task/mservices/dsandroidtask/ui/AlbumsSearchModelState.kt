package com.hadia.task.mservices.dsandroidtask.ui

data class AlbumsSearchModelState(
    val searchText: String = "",
    val showProgressBar: Boolean = true,
    val isSearching: Boolean = false,
    val isSearchingListEmpty: Boolean = false
) {
    companion object {
        val Empty = AlbumsSearchModelState()
    }
}
