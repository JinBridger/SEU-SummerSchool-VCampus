package app.vcampus.client.scene

import app.vcampus.client.viewmodel.ShopViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.*
import app.vcampus.client.viewmodel.LibraryViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel

val shopSideBarItem = listOf(
    SideBarItem(true, "购物", "", Icons.Default.Info),
    SideBarItem(false, "购物页面", "选择商品", Icons.Default.ShoppingBasket),

    SideBarItem(true, "订单相关", "", Icons.Default.Info),
    SideBarItem(false, "我的订单", "查看所有订单", Icons.Default.FormatListBulleted),
)

@Composable
fun ShopStatusForUser(viewModel: ShopViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(shopSideBarItem)
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
                        pageTitle("商店", "购买物品")
                    }
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