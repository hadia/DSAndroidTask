package com.hadia.task.mservices.dsandroidtask

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hadia.task.mservices.dsandroidtask.ui.albums.AlbumContent
import com.hadia.task.mservices.dsandroidtask.ui.composables.SearchView
import com.hadia.task.mservices.dsandroidtask.ui.theme.AndroidTaskTheme
import com.hadia.task.mservices.dsandroidtask.ui.theme.Black94
import dagger.hilt.android.AndroidEntryPoint
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTaskTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MyApp(viewModel)
                }
            }
        }
    }
}

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun MyApp(vm: MainViewModel) {

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val state = rememberCollapsingToolbarScaffoldState()
    var enabled by remember { mutableStateOf(true) }
    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize()
            .background(Black94)
            .padding(0.dp, 40.dp, 0.dp, 0.dp),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbarModifier = Modifier.background(Black94),
        enabled = enabled,
        toolbar = {
            SearchView(stringResource(R.string.discover), textState, state)
        }, body = {
            AlbumContent(vm.albums)
        }
    )
}
