package app.vcampus.client.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.SideBar
import app.vcampus.client.scene.components.shadowCustom
import app.vcampus.client.scene.subscene.blankSubscene
import app.vcampus.client.scene.subscene.library.myBookSubscene
import app.vcampus.client.scene.subscene.library.reserveReturnBookSubscene
import app.vcampus.client.scene.subscene.library.searchBookSubscene
import app.vcampus.client.viewmodel.LibraryViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel


@Composable
fun LibraryStatusForUser(viewModel: LibraryViewModel) {
    val librarySideBarItem = viewModel.librarySideBarItem
    val currentSubscene = remember { mutableStateOf(-1) }

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(librarySideBarItem) {
            (0..<librarySideBarItem.size).forEach { i ->
                librarySideBarItem[i] = librarySideBarItem[i].copy(
                        isChosen = false)
            }
            librarySideBarItem[it] = librarySideBarItem[it].copy(
                    isChosen = true)
            currentSubscene.value = it
        }
        Box(
                modifier = Modifier.fillMaxHeight().fillMaxWidth().shadowCustom(
                        offsetX = 3.dp, blurRadius = 10.dp)
                        .background(
                                Color.White
                        )
                        .padding(horizontal = 100.dp)
        ) {
            when (currentSubscene.value) {
                -1 -> blankSubscene()
                1 -> searchBookSubscene(viewModel)
                2 -> myBookSubscene(viewModel)
                4 -> reserveReturnBookSubscene(viewModel)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun LibraryScene(
        navi: Navigator
) {
    val viewModel = viewModel(LibraryViewModel::class, listOf()) {
        LibraryViewModel()
    }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

//    Scaffold(scaffoldState = scaffoldState, topBar = {
//        TopBar("学籍管理")
//    }) {
//    Row {
//        NavRail(navi, "/library")
//            Box(Modifier.fillMaxSize()) {
//                Box(Modifier.width(800.dp).align(Alignment.TopCenter)) {
    LibraryStatusForUser(viewModel)
//                }
//            }
//    }
//    }
}