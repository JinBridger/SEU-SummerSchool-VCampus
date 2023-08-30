package app.vcampus.client.scene

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
import app.vcampus.client.scene.subscene.shop.myOrderSubscene
import app.vcampus.client.scene.subscene.shop.selectItemSubscene
import app.vcampus.client.viewmodel.ShopViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel


@Composable
fun ShopStatusForUser(viewModel: ShopViewModel) {
    val shopSideBarItem = viewModel.shopSideBarItem
    val currentSubscene = remember { mutableStateOf(-1) }

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(shopSideBarItem) {
            (0..<shopSideBarItem.size).forEach { i ->
                shopSideBarItem[i] = shopSideBarItem[i].copy(isChosen = false)
            }
            shopSideBarItem[it] = shopSideBarItem[it].copy(isChosen = true)
            currentSubscene.value = it
        }
        Box(
                modifier = Modifier.fillMaxHeight().fillMaxWidth().shadowCustom(
                        offsetX = 3.dp, blurRadius = 10.dp)
                        .background(
                                Color.White
                        )
                        .padding(horizontal = 100.dp)
        ) {
            when (currentSubscene.value) {
                -1 -> blankSubscene()
                1 -> selectItemSubscene(viewModel)
                3 -> myOrderSubscene(viewModel)
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