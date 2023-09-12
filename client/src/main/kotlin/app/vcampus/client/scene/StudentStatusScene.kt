package app.vcampus.client.scene

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.SideBar
import app.vcampus.client.scene.components.shadowCustom
import app.vcampus.client.scene.subscene.blankSubscene
import app.vcampus.client.scene.subscene.studentstatus.modifyStudentStatusSubscene
import app.vcampus.client.scene.subscene.studentstatus.studentStatusSubscene
import app.vcampus.client.viewmodel.StudentStatusViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel

/**
 * student status scene
 *
 * @param navi navigator
 */
@ExperimentalMaterialApi
@Composable
fun StudentStatusScene(navi: Navigator) {
    val viewModel = viewModel(StudentStatusViewModel::class, listOf()) {
        StudentStatusViewModel()
    }

    val studentStatusSideBarItem = viewModel.studentStatusSideBarItem
    val currentSubscene = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(studentStatusSideBarItem) {
            (0..<studentStatusSideBarItem.size).forEach { i ->
                studentStatusSideBarItem[i] = studentStatusSideBarItem[i].copy(
                        isChosen = false
                )
            }
            studentStatusSideBarItem[it] = studentStatusSideBarItem[it].copy(
                    isChosen = true
            )
            currentSubscene.value = studentStatusSideBarItem[it].heading
        }
        Box(
                modifier = Modifier.fillMaxHeight().fillMaxWidth().shadowCustom(
                        offsetX = 3.dp, blurRadius = 10.dp
                ).background(
                        Color.White
                ).padding(horizontal = 100.dp)
        ) {
            Crossfade(currentSubscene.value) {
                when (it) {
                    "" -> blankSubscene()
                    "我的学籍信息" -> studentStatusSubscene(viewModel)
                    "修改学籍信息" -> modifyStudentStatusSubscene(viewModel)
                }
            }
        }
    }
}