package app.vcampus.client.scene.subscene.shop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.shopItemSellListItem
import app.vcampus.client.viewmodel.ShopViewModel
import app.vcampus.server.entity.StoreItem
import java.time.LocalDateTime
import app.vcampus.server.entity.StoreTransaction
import com.seanproctor.datatable.paging.rememberPaginatedDataTableState
import java.time.ZonedDateTime
import java.util.*
import app.vcampus.server.utility.DateUtility
import javax.management.monitor.StringMonitorMBean

@Composable
fun dashboardSubscene(viewModel: ShopViewModel) {
    val testItem = StoreItem()
    testItem.itemName = "测试名称"
    testItem.pictureLink = "https://i.dawnlab.me/05f0f5392e8efc95de553bafa2e30722.png"
    testItem.price = 279900
    testItem.salesVolume = 100
    testItem.stock = 100

    val datetime = Date.from(ZonedDateTime.now().toInstant())
    val copareString1 = DateUtility.fromDate(datetime)
    var cost = mutableStateOf(0)
    var number1 = mutableStateOf(0)
    val arrItem = arrayOfNulls<StoreItem>(number1.value).toMutableList()

    println(copareString1)
//    println(datetime)

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
                viewModel.totalOrderItems.forEach {
                    item {
                        val totalCost = mutableStateOf(0)
                        val tempNumber = mutableStateOf(0)
                        it.value.forEach {
                            val compareStirng2 = DateUtility.fromDate(it.time)
                                if (copareString1 == compareStirng2) {
                                    totalCost.value += it.amount * it.itemPrice
                                    cost.value = totalCost.value
                                    tempNumber.value += 1
                                    number1.value = tempNumber.value
                                    val subStoreItem = FakeRepository.getStoreItemByUuid(it.itemUUID.toString())
//                                    arrItem.add(subStoreItem)
                                }
                        }
                    }
                }
//                println(arrItem)
                item {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            "今日销售额",
                            fontWeight = FontWeight(700),
                            fontSize = 20.sp
                        )
                        Text(
                            "($copareString1)",
                            fontWeight = FontWeight(700),
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.weight(1F))
                        Column {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("￥", fontSize = 14.sp)
                        }
                        Text(
                            String.format("%.2f", cost.value/ 100.0),
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
                }
            }
        }
    }

//@Composable
//fun dashboardSubscene(_viewModel: ShopViewModel) {
//    val testItem = StoreItem()
//    testItem.itemName = "测试名称"
//    testItem.pictureLink = "https://i.dawnlab.me/05f0f5392e8efc95de553bafa2e30722.png"
//    testItem.price = 279900
//    testItem.salesVolume = 100
//    testItem.stock = 100
//
//    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 100.dp)) {
//        Row(horizontalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxWidth()) {
//            LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                item {
//                    Spacer(Modifier.height(80.dp))
//                    pageTitle("查看后台信息", "仪表盘")
//                    Spacer(Modifier.height(20.dp))
//                }
//
//                item {
//                    Row(Modifier.fillMaxWidth()) {
//                        Text("今日销售额", fontWeight = FontWeight(700),
//                                fontSize = 20.sp)
//                        Spacer(Modifier.weight(1F))
//                        Column {
//                            Spacer(modifier = Modifier.height(4.dp))
//                            Text("￥", fontSize = 14.sp)
//                        }
//                        Text(String.format("%.2f", 9999999 / 100.0),
//                                fontWeight = FontWeight(700),
//                                fontSize = 24.sp)
//                    }
//                }
//
//                item {
//                    Spacer(Modifier.height(20.dp))
//                    Text("商品销售情况", fontWeight = FontWeight(700),
//                            fontSize = 20.sp)
//                    Spacer(Modifier.height(10.dp))
//                }
//
//                (0..10).forEach {
//                    item {
//                        shopItemSellListItem(testItem, viewModel)
//                        Spacer(Modifier.height(10.dp))
//                    }
//                }
//
//                item {
//                    Spacer(Modifier.height(80.dp))
//                }
//            }
//        }
//    }
//}