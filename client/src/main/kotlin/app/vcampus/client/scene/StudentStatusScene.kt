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
import app.vcampus.client.viewmodel.StudentStatusViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel


@Composable
fun StudentStatusForStudent(viewModel: StudentStatusViewModel) {
    val studentStatusSideBarItem = viewModel.studentStatusSideBarItem
    val currentSubscene = remember { mutableStateOf(-1) }

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(studentStatusSideBarItem) {
            (0..<studentStatusSideBarItem.size).forEach { i ->
                studentStatusSideBarItem[i] = studentStatusSideBarItem[i].copy(isChosen = false)
            }
            studentStatusSideBarItem[it] = studentStatusSideBarItem[it].copy(isChosen = true)
            currentSubscene.value = it
        }
        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().shadowCustom(offsetX = 3.dp, blurRadius = 10.dp).background(Color.White).padding(horizontal = 100.dp)) {
            when (currentSubscene.value) {
                -1 -> blankSubscene()
                1 -> studentStatusSubscene(viewModel)
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun StudentStatusScene(navi: Navigator) {
    val viewModel = viewModel(StudentStatusViewModel::class, listOf()) {
        StudentStatusViewModel()
    }

    StudentStatusForStudent(viewModel)
}