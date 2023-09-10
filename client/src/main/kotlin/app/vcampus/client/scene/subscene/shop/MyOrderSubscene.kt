package app.vcampus.client.scene.subscene.shop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.shopTransactionListItem
import app.vcampus.client.viewmodel.ShopViewModel
import app.vcampus.server.entity.StoreTransaction
import app.vcampus.server.utility.DateUtility

@Composable
fun listOfTransactions(list: List<StoreTransaction>) {
    Card(
        modifier = Modifier.fillMaxWidth().border(
            1.dp,
            color = Color.LightGray, shape = RoundedCornerShape(4.dp)
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            list.forEach { st ->
                key(st.uuid) {
                    shopTransactionListItem(st)
                }
            }
        }
    }
}

@Composable
fun myOrderSubscene(viewModel: ShopViewModel) {
    LaunchedEffect(Unit) {
        viewModel.myOrders.getOrders()
    }

    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 100.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Spacer(Modifier.height(80.dp))
                    pageTitle("我的订单", "查看所有订单")
                    Spacer(Modifier.height(40.dp))
                }

                var currentDate = ""
                val totalMap = mutableMapOf<String, Int>()
                val tempList = mutableListOf<StoreTransaction>()
                viewModel.myOrders.orders.forEach {
                    if (!totalMap.containsKey(DateUtility.fromDate(it.time))) {
                        totalMap[DateUtility.fromDate(it.time)] = 0
                    }

                    if (currentDate != DateUtility.fromDate(it.time)) {
                        if (currentDate != "" && tempList.isNotEmpty()) {
                            item {
                                listOfTransactions(tempList)
                            }
                        }

                        tempList.clear()
                        currentDate = DateUtility.fromDate(it.time)

                        item {
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    text = currentDate,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(700)
                                )
                                Spacer(Modifier.weight(1F))
                                Text(
                                    text = "共 " + String.format(
                                        "%.2f",
                                        totalMap[currentDate]?.div(100.0) ?: 0
                                    ) + " 元",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(700)
                                )
                            }
                            Spacer(Modifier.height(20.dp))
                        }
                    }

                    tempList.add(it)
                    totalMap[currentDate] = totalMap[currentDate]!! + it.itemPrice * it.amount
                }

                item {
                    if (currentDate != "" && tempList.isNotEmpty()) {
                        listOfTransactions(tempList)
                    }
                }

//                item{
//                    viewModel.searchTransaction.Transactions.forEach{
//                        Spacer(Modifier.height(20.dp))
//                        Card(modifier = Modifier.fillMaxWidth().border(1.dp,
//                            color = Color.LightGray, shape = RoundedCornerShape(4.dp))) {
//                            Column(modifier = Modifier.fillMaxWidth()) {
//                                it.value.forEach {
//                                    shopTransactionListItem(it,viewModel)
//                                }
//                            }
//                        }
//                    }
//                    Spacer(Modifier.height(40.dp))
//                }
//                item {
//                    Spacer(modifier = Modifier.height(80.dp))
//                }
            }
        }
    }
}