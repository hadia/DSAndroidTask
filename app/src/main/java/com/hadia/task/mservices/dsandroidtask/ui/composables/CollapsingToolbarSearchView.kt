package com.hadia.task.mservices.dsandroidtask.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hadia.task.mservices.dsandroidtask.R
import com.hadia.task.mservices.dsandroidtask.ui.theme.Black94
import com.hadia.task.mservices.dsandroidtask.ui.theme.Grey24
import me.onebone.toolbar.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CollapsingToolbarSearchView(
    title: String,
    searchText: String,
    onSearchTextChanged: (String) -> Unit = {},
    placeholderText: String = stringResource(R.string.search),
    onClearClick: () -> Unit = {},
    isLoading: Boolean,
    collapsingToolbarState: CollapsingToolbarScaffoldState,
) {
    val progress = collapsingToolbarState.toolbarState.progress // how much the toolbar is expanded (0: collapsed, 1: expanded)
    val textSize = (18 + (34 - 18) * progress).sp
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.padding(16.dp, 100.dp, 16.dp, 16.dp)
            .background(Black94)
            .fillMaxWidth()
    ) {

        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            placeholder = {
                Text(text = placeholderText)
            },
            modifier =
            Modifier
                .background(Black94)
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = progress
                }
                .onFocusChanged { focusState ->
                    showClearButton = (focusState.isFocused)
                }
                .focusRequester(focusRequester),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(7.dp)
                        .size(24.dp)
                        .graphicsLayer {
                            alpha = if (isLoading) 0f else 1f
                        }
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = {
                        onClearClick()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
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
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            })
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(4.dp)
                .size(30.dp)
                .graphicsLayer {
                    alpha = if (!isLoading) 0f else 1f
                }
        ) {
            CircularProgressIndicator()
        }
    }

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
    val state = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarSearchView(
        title = "Discover", searchText = "",
        placeholderText = "Search users",
        onSearchTextChanged = { },
        onClearClick = { },
        isLoading = true,
        collapsingToolbarState = state
    )
}
