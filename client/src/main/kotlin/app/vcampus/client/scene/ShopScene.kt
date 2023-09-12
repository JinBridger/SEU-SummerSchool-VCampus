package app.vcampus.client.scene

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import app.vcampus.client.scene.subscene.shop.*
import app.vcampus.client.viewmodel.ShopViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel

/**
 * shop scene
 *
 * @param navi navigator
 */
@ExperimentalMaterialApi
@Composable
fun ShopScene(
    navi: Navigator
) {
    val viewModel = viewModel(ShopViewModel::class, listOf()) {
        ShopViewModel()
    }
    val shopSideBarItem = viewModel.shopSideBarItem
    val currentSubscene = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(shopSideBarItem) {
            (0..<shopSideBarItem.size).forEach { i ->
                shopSideBarItem[i] = shopSideBarItem[i].copy(isChosen = false)
            }
            shopSideBarItem[it] = shopSideBarItem[it].copy(isChosen = true)
            currentSubscene.value = shopSideBarItem[it].heading
        }
        Box(
                modifier = Modifier.fillMaxHeight().fillMaxWidth().shadowCustom(
                        offsetX = 3.dp, blurRadius = 10.dp
                )
                        .background(
                                Color.White
                        )
        ) {
            Crossfade(currentSubscene.value) {
                when (it) {
                    "" -> blankSubscene()
                    "购物页面" -> selectItemSubscene(viewModel)
                    "我的订单" -> myOrderSubscene(viewModel)
                    "添加商品" -> addItemSubscene(viewModel)
                    "修改商品" -> modifyItemSubscene(viewModel)
                    "查看后台信息" -> dashboardSubscene(viewModel)
                }
            }
        }
    }
}