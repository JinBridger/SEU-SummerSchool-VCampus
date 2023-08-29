package app.vcampus.client.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.*
import app.vcampus.client.viewmodel.LibraryViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel




@Composable
fun LibraryStatusForUser(viewModel: LibraryViewModel) {
    val librarySideBarItem = mutableStateListOf(
        SideBarItem(true, "查询", "", Icons.Default.Info, false),
        SideBarItem(false, "查询图书", "查找图书馆藏书", Icons.Default.Search, false),
        SideBarItem(false, "我的书籍", "查看已借阅书籍", Icons.Default.MenuBook, false),

        SideBarItem(true, "借还相关", "", Icons.Default.Info, false),
        SideBarItem(false, "预约还书", "预约还书时间", Icons.Default.Event, false),
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(librarySideBarItem) {
            (0..<librarySideBarItem.size).forEach { i ->
                librarySideBarItem[i] = librarySideBarItem[i].copy(isChosen = false)
            }
            librarySideBarItem[it] = librarySideBarItem[it].copy(isChosen = true)
        }
        Box(
            modifier = Modifier.fillMaxHeight().fillMaxWidth().shadowCustom(offsetX = 3.dp, blurRadius = 10.dp)
                .background(
                    Color.White
                )
                .padding(horizontal = 100.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Spacer(Modifier.height(80.dp))
                        pageTitle("图书馆", "查找图书")
                    }
                }
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