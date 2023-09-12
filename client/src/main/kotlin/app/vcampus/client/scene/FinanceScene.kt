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
import app.vcampus.client.scene.subscene.finance.myBillsSubscene
import app.vcampus.client.scene.subscene.finance.staffSubscene
import app.vcampus.client.viewmodel.FinanceViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel


/**
 * finance scene, modify balance in the card
 *
 * @param navi navigator
 */
@ExperimentalMaterialApi
@Composable
fun FinanceScene(navi: Navigator) {
    val viewModel = viewModel(FinanceViewModel::class, listOf()) {
        FinanceViewModel()
    }

    val financeSideBarItem = viewModel.financeSideBarItem
    val currentSubscene = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(financeSideBarItem) {
            (0..<financeSideBarItem.size).forEach { i ->
                financeSideBarItem[i] = financeSideBarItem[i].copy(
                    isChosen = false
                )
            }
            financeSideBarItem[it] = financeSideBarItem[it].copy(
                isChosen = true
            )
            currentSubscene.value = financeSideBarItem[it].heading
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
                    "一卡通" -> myBillsSubscene(viewModel)
                    "一卡通管理" -> staffSubscene(viewModel)
                }
            }
        }
    }
}