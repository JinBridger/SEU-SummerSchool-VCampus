package app.vcampus.client.scene

import androidx.compose.animation.Crossfade
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
import app.vcampus.client.scene.subscene.library.*
import app.vcampus.client.viewmodel.LibraryViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel

/**
 * library scene
 *
 * @param navi navigator
 */
@ExperimentalMaterialApi
@Composable
fun LibraryScene(
    navi: Navigator
) {
    val viewModel = viewModel(LibraryViewModel::class, listOf()) {
        LibraryViewModel()
    }

    val librarySideBarItem = viewModel.librarySideBarItem
    val currentSubscene = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(librarySideBarItem) {
            (0..<librarySideBarItem.size).forEach { i ->
                librarySideBarItem[i] = librarySideBarItem[i].copy(
                        isChosen = false
                )
            }
            librarySideBarItem[it] = librarySideBarItem[it].copy(
                    isChosen = true
            )
            currentSubscene.value = librarySideBarItem[it].heading
        }
        Box(
                modifier = Modifier.fillMaxHeight().fillMaxWidth().shadowCustom(
                        offsetX = 3.dp, blurRadius = 10.dp
                )
                        .background(
                                Color.White
                        )
                        .padding(horizontal = 100.dp)
        ) {
            Crossfade(currentSubscene.value) {
                when (it) {
                    "" -> blankSubscene()
                    "查询图书" -> searchBookSubscene(viewModel)
                    "我的书籍" -> myBookSubscene(viewModel)
                    "添加图书" -> addBookSubscene(viewModel)
                    "修改图书" -> modifyBookSubscene(viewModel)
                    "办理还书" -> returnSubscene(viewModel)
                    "办理借书" -> borrowSubscene(viewModel)
                }
            }
        }
    }
}