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
import app.vcampus.client.viewmodel.TeachingAffairsViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel



@Composable
fun TeachingAffairsStatusForUser(viewModel: TeachingAffairsViewModel) {
    val teachingAffairsSideBarItem = mutableStateListOf(
        SideBarItem(true, "教务信息", "", Icons.Default.Info, false),
        SideBarItem(false, "我的课表", "查看个人课表", Icons.Default.CalendarMonth, false),
        SideBarItem(false, "我的成绩", "查看个人成绩单", Icons.Default.FileOpen, false),

        SideBarItem(true, "教务工具", "", Icons.Default.Info, false),
        SideBarItem(false, "选课", "进入选课页面", Icons.Default.Checklist, false),
        SideBarItem(false, "评教", "进入评教页面", Icons.Default.Diversity1, false)
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(teachingAffairsSideBarItem) {
            (0..<teachingAffairsSideBarItem.size).forEach { i ->
                teachingAffairsSideBarItem[i] = teachingAffairsSideBarItem[i].copy(isChosen = false)
            }
            teachingAffairsSideBarItem[it] = teachingAffairsSideBarItem[it].copy(isChosen = true)
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
                        pageTitle("教务处", "教务信息")
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TeachingAffairsScene(
    navi: Navigator
) {
    val viewModel = viewModel(TeachingAffairsViewModel::class, listOf()) {
        TeachingAffairsViewModel()
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
    TeachingAffairsStatusForUser(viewModel)
//                }
//            }
//    }
//    }
}