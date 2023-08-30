package app.vcampus.client.viewmodel


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.runtime.mutableStateListOf
import app.vcampus.client.scene.components.SideBarItem
import moe.tlaster.precompose.viewmodel.ViewModel

class ShopViewModel() : ViewModel() {
    val shopSideBarItem = mutableStateListOf(
            SideBarItem(true, "购物", "", Icons.Default.Info, false),
            SideBarItem(false, "购物页面", "选择商品", Icons.Default.ShoppingBasket, false),

            SideBarItem(true, "订单相关", "", Icons.Default.Info, false),
            SideBarItem(false, "我的订单", "查看所有订单", Icons.Default.FormatListBulleted, false),
    )
}