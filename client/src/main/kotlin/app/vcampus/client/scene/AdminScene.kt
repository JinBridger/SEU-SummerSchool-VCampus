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
import app.vcampus.client.scene.subscene.admin.addUserSubscene
import app.vcampus.client.scene.subscene.admin.modifyUserSubscene
import app.vcampus.client.scene.subscene.blankSubscene
import app.vcampus.client.viewmodel.AdminViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel

/**
 * admin scene, add / modify user information
 *
 * @param navi navigator
 */
@ExperimentalMaterialApi
@Composable
fun AdminScene(navi: Navigator) {
    val viewModel = viewModel(AdminViewModel::class, listOf()) {
        AdminViewModel()
    }

    val adminSideBarItem = viewModel.adminSideBarItem
    val currentSubscene = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(adminSideBarItem) {
            (0..<adminSideBarItem.size).forEach { i ->
                adminSideBarItem[i] = adminSideBarItem[i].copy(
                    isChosen = false
                )
            }
            adminSideBarItem[it] = adminSideBarItem[it].copy(
                isChosen = true
            )
            currentSubscene.value = adminSideBarItem[it].heading
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
                    "添加账户" -> addUserSubscene(viewModel)
                    "修改账户" -> modifyUserSubscene(viewModel)
                }
            }
        }
    }
}