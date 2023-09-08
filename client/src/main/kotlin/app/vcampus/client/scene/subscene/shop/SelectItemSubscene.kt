package app.vcampus.client.scene.subscene.shop

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Paid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun selectItemSubscene(viewModel: ShopViewModel) {
    val openState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
            scaffoldState = openState,
            sheetContent = {
                Box(Modifier.fillMaxWidth().height(56.dp).background(
                        MaterialTheme.colors.primary)
                ) {
                    Row(modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically) {
                        Spacer(Modifier.width(8.dp))
                        AnimatedContent(
                                targetState = openState.bottomSheetState.isExpanded,
                                transitionSpec = {
                                    slideInVertically(
                                            initialOffsetY = { 40 }) + fadeIn() with slideOutVertically(
                                            targetOffsetY = { -40 }) + fadeOut()
                                }
                        ) {
                            if (it) {
                                TextButton(onClick = {
                                    coroutineScope.launch {
                                        openState.bottomSheetState.collapse()
                                    }
                                },
                                        colors = ButtonDefaults.buttonColors(
                                                contentColor = Color.White)) {
                                    Icon(Icons.Default.Paid, "")
                                    Spacer(Modifier.width(6.dp))
                                    Text("下滑以浏览")
                                    Spacer(Modifier.width(2.dp))
                                    Icon(Icons.Default.KeyboardArrowDown, "")
                                }
                            } else {
                                TextButton(onClick = {
                                    coroutineScope.launch {
                                        openState.bottomSheetState.expand()
                                    }
                                },
                                        colors = ButtonDefaults.buttonColors(
                                                contentColor = Color.White)) {
                                    Icon(Icons.Default.Paid, "")
                                    Spacer(Modifier.width(6.dp))
                                    Text("上滑以结算")
                                    Spacer(Modifier.width(2.dp))
                                    Icon(Icons.Default.KeyboardArrowUp, "")
                                }
                            }
                        }
                        Spacer(Modifier.weight(1F))
                        AnimatedContent(
                                targetState = viewModel.chosenItemsCount.value,
                                transitionSpec = {
                                    slideInVertically(
                                            initialOffsetY = { 40 }) + fadeIn() with slideOutVertically(
                                            targetOffsetY = { -40 }) + fadeOut()
                                }
                        ) {
                            Text("已选择 ${viewModel.chosenItemsCount.value} 项商品",
                                    color = Color.White)
                        }
                        Spacer(Modifier.width(16.dp))
                        AnimatedContent(
                                targetState = viewModel.chosenItemsPrice.value,
                                transitionSpec = {
                                    slideInVertically(
                                            initialOffsetY = { 40 }) + fadeIn() with slideOutVertically(
                                            targetOffsetY = { -40 }) + fadeOut()
                                }
                        ) {
                            Text("共 ${
                                String.format("%.2f",
                                        viewModel.chosenItemsPrice.value / 100.0)
                            } 元",
                                    color = Color.White,
                                    fontWeight = FontWeight(700))
                        }
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
                                    AnimatedContent(
                                            targetState = viewModel.chosenItemsPrice.value,
                                            transitionSpec = {
                                                slideInVertically(
                                                        initialOffsetY = { 100 }) + fadeIn() with slideOutVertically(
                                                        targetOffsetY = { -100 }) + fadeOut()
                                            }
                                    ) {
                                        pageTitle(
                                                "共 ${
                                                    String.format("%.2f",
                                                            viewModel.chosenItemsPrice.value / 100.0)
                                                } 元",
                                                "已选择 ${viewModel.chosenItemsCount.value} 件商品")
                                    }
                                    Spacer(Modifier.weight(1F))
                                    Button(onClick = {

                                    }) {
                                        Text("立即支付")
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }

                            item {
                                Card(modifier = Modifier.fillMaxWidth().border(1.dp,
                                        color = Color.LightGray, shape = RoundedCornerShape(4.dp))) {
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        viewModel.chosenShopItems.forEach {
                                            if (it.stock != 0) {
                                                shopCheckListItem(it, viewModel)
                                            }
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

                    (0..<viewModel.totalShopItems.size.floorDiv(3)).forEach {
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column {
                                    shopItemCard(
                                            viewModel.totalShopItems[it * 3],
                                            viewModel)
                                }
                                Spacer(Modifier.weight(1F))
                                Column {
                                    shopItemCard(
                                            viewModel.totalShopItems[it * 3 + 1],
                                            viewModel)
                                }
                                Spacer(Modifier.weight(1F))
                                Column {
                                    shopItemCard(
                                            viewModel.totalShopItems[it * 3 + 2],
                                            viewModel)
                                }
                            }
                        }
                    }

                    if (viewModel.totalShopItems.size.mod(3) == 2) {
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column {
                                    shopItemCard(
                                            viewModel.totalShopItems[viewModel.totalShopItems.size - 2],
                                            viewModel)
                                }
                                Spacer(Modifier.weight(1F))
                                Column {
                                    shopItemCard(
                                            viewModel.totalShopItems[viewModel.totalShopItems.size - 1],
                                            viewModel)
                                }
                                Spacer(Modifier.weight(1F))
                                Column {
                                    Spacer(Modifier.width(250.dp))
                                }
                            }
                        }
                    }

                    if (viewModel.totalShopItems.size.mod(3) == 1) {
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column {
                                    shopItemCard(
                                            viewModel.totalShopItems[viewModel.totalShopItems.size - 1],
                                            viewModel)
                                }
                                Spacer(Modifier.weight(1F))
                                Column {
                                    Spacer(Modifier.width(250.dp))
                                }
                                Spacer(Modifier.weight(1F))
                                Column {
                                    Spacer(Modifier.width(250.dp))
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