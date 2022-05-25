package com.hadia.task.mservices.dsandroidtask.ui.albums

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hadia.task.mservices.dsandroidtask.R
import com.hadia.task.mservices.dsandroidtask.data.model.toAlbum
import com.hadia.task.mservices.dsandroidtask.domain.model.Album
import com.hadia.task.mservices.dsandroidtask.domain.model.DataProvider
import com.hadia.task.mservices.dsandroidtask.ui.theme.White45
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumListItem(albumUIModel: Album) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
        ) {
            AlbumImage(albumUIModel)
            ListenerLayout(albumUIModel)
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {

                albumUIModel.name?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }

                albumUIModel.genres?.joinToString(" , ")?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun ListenerLayout(albumUIModel: Album) {
    Row(
        modifier = Modifier.padding(all = 8.dp)
            .background(White45, shape = RoundedCornerShape(9.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.ic_listenericon),
            contentDescription = null,
            modifier = Modifier.padding(
                start = 5.dp,
                end = 1.dp, top = 5.dp
            )
        )
        Text(
            text = albumUIModel.listenerCount.toString(),
            modifier = Modifier.padding(3.dp), style = MaterialTheme.typography.labelSmall,
            color = Color.Black
        )
    }
}

@Composable
private fun AlbumImage(Album: Album) {
    GlideImage(
        imageModel = Album.artworkUrl,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .drawWithCache {
                val gradient = Brush.verticalGradient(
                    colors = listOf(Color(0x00000000), Color(0xCC000000)),
                    startY = 0f,
                    endY = size.height,
                    tileMode = TileMode.Repeated
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.Multiply)
                }
            },
        // shows an indicator while loading an image.
        loading = {
            Box(modifier = Modifier.matchParentSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewAlbumUIModelItem() {
    val albumUIModel = DataProvider.list.data?.sessions?.map { it.toAlbum() }!!
    AlbumListItem(albumUIModel = albumUIModel?.first())
}
