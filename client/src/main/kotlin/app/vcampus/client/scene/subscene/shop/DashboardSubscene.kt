package app.vcampus.client.scene.subscene.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.shopItemSellListItem
import app.vcampus.client.viewmodel.ShopViewModel

/**
 * dashboard subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun dashboardSubscene(viewModel: ShopViewModel) {
    LaunchedEffect(Unit) {
        viewModel.selectItem.getAllItems()
        viewModel.dashboard.getTodaySales()
    }

    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 100.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Spacer(Modifier.height(80.dp))
                    pageTitle("查看后台信息", "仪表盘")
                    Spacer(Modifier.height(20.dp))
                }

                item {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            "今日销售额", fontWeight = FontWeight(700),
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.weight(1F))
                        Column {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("￥", fontSize = 14.sp)
                        }
                        Text(
                            String.format("%.2f", viewModel.dashboard.todaySales.value / 100.0),
                            fontWeight = FontWeight(700),
                            fontSize = 24.sp
                        )
                    }
                }

                item {
                    Spacer(Modifier.height(20.dp))
                    Text(
                        "商品销售情况", fontWeight = FontWeight(700),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(10.dp))
                }
                viewModel.selectItem.totalShopItems.forEach {
                    item(it.uuid) {
                        shopItemSellListItem(it, viewModel)
                        Spacer(Modifier.height(10.dp))
                    }
                }

                item {
                    Spacer(Modifier.height(80.dp))
                }
            }
        }
    }
}