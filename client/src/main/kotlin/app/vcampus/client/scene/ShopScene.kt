package app.vcampus.client.scene

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
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


@Composable
fun ShopStatusForUser(viewModel: ShopViewModel) {
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
                        offsetX = 3.dp, blurRadius = 10.dp)
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

@ExperimentalMaterialApi
@Composable
fun ShopScene(
        navi: Navigator
) {
    val viewModel = viewModel(ShopViewModel::class, listOf()) {
        ShopViewModel()
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
    ShopStatusForUser(viewModel)
//                }
//            }
//    }
//    }
}