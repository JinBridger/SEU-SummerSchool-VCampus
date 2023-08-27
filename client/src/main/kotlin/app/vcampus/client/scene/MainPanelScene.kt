package app.vcampus.client.scene

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import app.vcampus.client.viewmodel.MainPanelViewModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModel

@ExperimentalMaterialApi
@Composable
fun MainPanelScene(
    id: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit,
) {
    val viewModel = viewModel(MainPanelViewModel::class, listOf(id)) {
        MainPanelViewModel(id)
    }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text("Detail")
                },
                navigationIcon = {
                    IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { onEdit.invoke() }) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    }
                },
            )
        },
        drawerContent = {
            Text("test")
        }
    ) {
        Column {
            val note by viewModel.note.collectAsState()
            ListItem {
                Text(text = note.title, style = MaterialTheme.typography.h5)
            }
            Divider()
            ListItem {
                Text(text = note.content)
            }
        }
    }
}