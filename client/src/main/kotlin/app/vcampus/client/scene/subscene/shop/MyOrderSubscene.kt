package app.vcampus.client.scene.subscene.shop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.shadowCustom
import app.vcampus.client.scene.components.shopTransactionListItem
import app.vcampus.client.viewmodel.ShopViewModel
import app.vcampus.server.entity.StoreItem

@Composable
fun myOrderSubscene(viewModel: ShopViewModel) {
    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 100.dp)) {
        Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Spacer(Modifier.height(80.dp))
                    pageTitle("我的订单", "查看所有订单")
                    Spacer(Modifier.height(40.dp))
                }

                viewModel.totalOrderItems.forEach {
                    item {
                        val totalCost = mutableStateOf(0)
                        it.value.forEach {
                            totalCost.value += it.amount * it.itemPrice
                        }

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                        text = it.key,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(700)
                                )
                                Spacer(Modifier.weight(1F))
                                Text(
                                        text = "共 "+String.format("%.2f",
                                                totalCost.value / 100.0) + " 元",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(700)
                                )
                            }
                            Spacer(Modifier.height(20.dp))
                            Card(modifier = Modifier.fillMaxWidth().border(1.dp,
                                    color = Color.LightGray, shape = RoundedCornerShape(4.dp))) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    it.value.forEach {
                                        if(it.amount != 0) {
                                            val item = FakeRepository.getStoreItemByUuid(it.itemUUID.toString())
                                            shopTransactionListItem(item, viewModel)
                                        }
                                    }
                                }
                            }
                            Spacer(Modifier.height(40.dp))
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}