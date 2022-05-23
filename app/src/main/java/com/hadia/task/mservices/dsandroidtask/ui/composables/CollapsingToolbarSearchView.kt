package com.hadia.task.mservices.dsandroidtask.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hadia.task.mservices.dsandroidtask.R
import com.hadia.task.mservices.dsandroidtask.ui.theme.Black94
import com.hadia.task.mservices.dsandroidtask.ui.theme.Grey24
import me.onebone.toolbar.*

@Composable
fun CollapsingToolbarSearchView(
    title: String,
    placeholderText: String = stringResource(R.string.search),
    textState: MutableState<TextFieldValue>,
    collapsingToolbarState: CollapsingToolbarScaffoldState,
) {
    val progress = collapsingToolbarState.toolbarState.progress // how much the toolbar is expanded (0: collapsed, 1: expanded)
    val textSize = (18 + (34 - 18) * progress).sp

    TextField(
        value = textState.value,
        onValueChange = { value ->
            textState.value = value
        },
        placeholder = {
            Text(text = placeholderText)
        },
        modifier =
        Modifier
            .background(Black94)
            .padding(16.dp, 100.dp, 16.dp, 16.dp)
            .fillMaxWidth()
            .graphicsLayer {
                // change alpha of Image as the toolbar expands
                alpha = progress
            },
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(7.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (textState.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        textState.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(7.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            containerColor = Grey24,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )

    Text(
        text = title,
        textAlign = if (progress == 0.0f)
            TextAlign.Center
        else
            TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 40.dp, 16.dp, 12.dp)
            .graphicsLayer {
                alpha = progress
                if (progress < 0.01f) {
                    alpha = 1f
                }
            },
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        fontSize = textSize,
        letterSpacing = 0.01.sp
    )
}

@Preview
@Composable
fun PreviewAlbumUIModelItem() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val state = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarSearchView("Discover", "Search", textState, state)
}
