package app.vcampus.client.scene.subscene.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Paid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.shadowCustom
import app.vcampus.client.scene.components.shopCheckListItem
import app.vcampus.client.scene.components.shopItemCard
import app.vcampus.client.viewmodel.ShopViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun selectItemSubscene(viewModel: ShopViewModel) {
    BottomSheetScaffold(
            sheetContent = {
                Box(Modifier.fillMaxWidth().height(56.dp).background(
                        MaterialTheme.colors.primary)
                ) {
                    Row(modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically) {
                        Spacer(Modifier.width(8.dp))
                        TextButton(onClick = {},
                                colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.White)) {
                            Icon(Icons.Default.Paid, "")
                            Spacer(Modifier.width(6.dp))
                            Text("上滑以结算")
                            Spacer(Modifier.width(2.dp))
                            Icon(Icons.Default.KeyboardArrowUp, "")
                        }
                        Spacer(Modifier.weight(1F))
                        Text("已选择 0 项商品", color = Color.White)
                        Spacer(Modifier.width(16.dp))
                        Text("共 0 元", color = Color.White,
                                fontWeight = FontWeight(700))
                        Spacer(Modifier.width(16.dp))
                    }
                }
                Box(modifier = Modifier.fillMaxSize().padding(
                        horizontal = 100.dp)) {
                    Row(horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()) {

                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            item {
                                Spacer(Modifier.height(80.dp))
                                Row {
                                    pageTitle("共 0 元", "已选择 0 件商品")
                                    Spacer(Modifier.weight(1F))
                                    Button(onClick = {}) {
                                        Text("立即支付")
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }

                            item {
                                Card(modifier = Modifier.fillMaxWidth().shadowCustom(
                                        blurRadius = 3.dp,
                                        shapeRadius = 3.dp)) {
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        (0..10).forEach {
                                            shopCheckListItem()
                                        }
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
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(horizontal = 100.dp)) {
            Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Spacer(Modifier.height(80.dp))
                        pageTitle("购物", "选择商品")
                    }

                    (0..10).forEach {
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column {
                                    shopItemCard()
                                }
                                Spacer(Modifier.weight(1F))
                                Column {
                                    shopItemCard()
                                }
                                Spacer(Modifier.weight(1F))
                                Column {
                                    shopItemCard()
                                }
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}