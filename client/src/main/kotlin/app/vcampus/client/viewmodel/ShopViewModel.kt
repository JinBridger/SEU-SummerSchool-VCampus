package app.vcampus.client.viewmodel


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.repository._StoreItem
import app.vcampus.client.repository.copy
import app.vcampus.client.scene.components.SideBarItem
import app.vcampus.server.entity.StoreItem
import moe.tlaster.precompose.viewmodel.ViewModel

class ShopViewModel() : ViewModel() {
    val identity = FakeRepository.user.roles.toList()

    val sideBarContent = (if (identity.contains("shop_user")) {
        listOf(SideBarItem(true, "购物", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "shop_user" -> listOf(
                    SideBarItem(false, "购物页面", "选择商品",
                            Icons.Default.ShoppingBasket, false)
            )

            else -> emptyList()
        }
    } + (if (identity.contains("shop_user")) {
        listOf(SideBarItem(true, "订单相关", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "shop_user" -> listOf(
                    SideBarItem(false, "我的订单", "查看所有订单",
                            Icons.Default.FormatListBulleted, false)
            )

            else -> emptyList()
        }
    } + (if (identity.contains("shop_staff")) {
        listOf(SideBarItem(true, "管理工具", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "shop_staff" -> listOf(
                    SideBarItem(false, "添加商品", "添加新的商品",
                            Icons.Default.LibraryAdd, false),
                    SideBarItem(false, "修改商品", "修改商品信息",
                            Icons.Default.AutoFixHigh, false),
                    SideBarItem(false, "查看后台信息", "仪表盘",
                            Icons.Default.Speed, false),
            )

            else -> emptyList()
        }
    }

    val shopSideBarItem = sideBarContent.toMutableStateList()

    // SelectItemSubscene

    val totalShopItems = FakeRepository.getAllStoreItems()

    val chosenShopItems = totalShopItems.map {
        it.copy(stock = 0)
    }.toMutableList()

//    val chosenShopItems: List<_StoreItem> = _chosenShopItems

    private val _chosenItemsCount = mutableStateOf(0)
    val chosenItemsCount: MutableState<Int> = _chosenItemsCount

    private val _chosenItemsPrice = mutableStateOf(0)
    val chosenItemsPrice: MutableState<Int> = _chosenItemsPrice

    val totalOrderItems = FakeRepository.getAllOrder()
}