package com.hadia.task.mservices.dsandroidtask.data.model

import com.hadia.task.mservices.dsandroidtask.domain.model.Album

data class AlbumsAPIModel(
    val `data`: Data? = null
)
data class Data(
    val sessions: List<Session>? = null
)

data class Session(
    val current_track: CurrentTrack? = null,
    val genres: List<String>? = null,
    val listener_count: Int? = null,
    val name: String? = null
)

data class CurrentTrack(
    val artwork_url: String? = null,
    val title: String? = null
)

fun Session.toAlbum() = Album(
    artworkUrl = this.current_track?.artwork_url,
    title = this.current_track?.title,
    genres = this.genres,
    listenerCount = this.listener_count,
    name = this.name
)
