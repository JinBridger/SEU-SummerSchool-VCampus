package app.vcampus.client.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.*
import app.vcampus.client.scene.subscene.blankSubscene
import app.vcampus.client.scene.subscene.studentstatus.studentStatusSubscene
import app.vcampus.client.scene.subscene.teachingaffairs.chooseClassSubscene
import app.vcampus.client.scene.subscene.teachingaffairs.evaluateTeacherSubscene
import app.vcampus.client.scene.subscene.teachingaffairs.myGradeSubscene
import app.vcampus.client.scene.subscene.teachingaffairs.myScheduleSubscene
import app.vcampus.client.viewmodel.TeachingAffairsViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel


@Composable
fun TeachingAffairsStatusForUser(viewModel: TeachingAffairsViewModel) {
    val teachingAffairsSideBarItem = viewModel.teachingAffairsSideBarItem
    val currentSubscene = remember { mutableStateOf(-1) }

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(teachingAffairsSideBarItem) {
            (0..<teachingAffairsSideBarItem.size).forEach { i ->
                teachingAffairsSideBarItem[i] = teachingAffairsSideBarItem[i].copy(isChosen = false)
            }
            teachingAffairsSideBarItem[it] = teachingAffairsSideBarItem[it].copy(isChosen = true)
            currentSubscene.value = it
        }
        Box(
                modifier = Modifier.fillMaxHeight().fillMaxWidth().shadowCustom(offsetX = 3.dp, blurRadius = 10.dp)
                        .background(
                                Color.White
                        )
                        .padding(horizontal = 100.dp)
        ) {
            when (currentSubscene.value) {
                -1 -> blankSubscene()
                1 -> myScheduleSubscene(viewModel)
                2 -> myGradeSubscene(viewModel)
                4 -> chooseClassSubscene(viewModel)
                5 -> evaluateTeacherSubscene(viewModel)
            }
//            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
//
//                LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                    item {
//                        Spacer(Modifier.height(80.dp))
//                        pageTitle("教务处", "教务信息")
//                    }
//                }
//            }
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