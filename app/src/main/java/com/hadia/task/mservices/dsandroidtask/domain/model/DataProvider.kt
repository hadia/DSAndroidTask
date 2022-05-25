package com.hadia.task.mservices.dsandroidtask.domain.model

import com.hadia.task.mservices.dsandroidtask.data.model.AlbumsAPIModel
import com.hadia.task.mservices.dsandroidtask.data.model.CurrentTrack
import com.hadia.task.mservices.dsandroidtask.data.model.Data
import com.hadia.task.mservices.dsandroidtask.data.model.Session

object DataProvider {
    val searchAlbum = Session(
        name = "blues",
        listener_count = 24,
        genres = listOf("Blues", "Soundtrack", "Alternative"),
        current_track = CurrentTrack(
            title = "When You're Smiling",
            artwork_url = "https://i.scdn.co/image/ab67616d0000b273c97b9bd31938531f99148a27"
        )
    )

    val list = AlbumsAPIModel(
        data = Data(
            sessions = listOf<Session>(
                searchAlbum,
                Session(
                    name = "latin",
                    listener_count = 57,
                    genres = listOf(
                        "Latino",
                        "Rock y Alternativo",
                        "Pop Latino"
                    ),
                    current_track = CurrentTrack(
                        title = "Tumbao",
                        artwork_url = "https://i.scdn.co/image/ee1b9144425bc91d0991719c252423048b68f81f"
                    )
                ),
            )
        )
    )
}
