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
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.shopCheckListItem
import app.vcampus.client.scene.components.shopItemCard
import app.vcampus.client.viewmodel.ShopViewModel
import kotlinx.coroutines.launch

/**
 * select item subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun selectItemSubscene(viewModel: ShopViewModel) {
    var keyword by remember { mutableStateOf("") }
    val openState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    val scope = rememberCoroutineScope()
    var searched by viewModel.selectItem.searched

    LaunchedEffect(Unit) {
        viewModel.selectItem.getAllItems()
    }

    BottomSheetScaffold(
        scaffoldState = openState,
        sheetContent = {
            Box(
                Modifier.fillMaxWidth().height(56.dp).background(
                    MaterialTheme.colors.primary
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                            TextButton(
                                onClick = {
                                    coroutineScope.launch {
                                        openState.bottomSheetState.collapse()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(Icons.Default.Paid, "")
                                Spacer(Modifier.width(6.dp))
                                Text("下滑以浏览")
                                Spacer(Modifier.width(2.dp))
                                Icon(Icons.Default.KeyboardArrowDown, "")
                            }
                        } else {
                            TextButton(
                                onClick = {
                                    coroutineScope.launch {
                                        openState.bottomSheetState.expand()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White
                                )
                            ) {
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
                        Text(
                            "已选择 ${viewModel.chosenItemsCount.value} 项商品",
                            color = Color.White
                        )
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
                        Text(
                            "共 ${
                                String.format(
                                    "%.2f",
                                    viewModel.chosenItemsPrice.value / 100.0
                                )
                            } 元",
                            color = Color.White,
                            fontWeight = FontWeight(700)
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                }
            }
            Box(
                modifier = Modifier.fillMaxSize().padding(
                    horizontal = 100.dp
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

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
                                            String.format(
                                                "%.2f",
                                                viewModel.chosenItemsPrice.value / 100.0
                                            )
                                        } 元",
                                        "已选择 ${viewModel.chosenItemsCount.value} 件商品"
                                    )
                                }
                                Spacer(Modifier.weight(1F))
                                val state = remember {
                                    SnackbarHostState()
                                }
                                SnackbarHost(hostState = state)
                                Button(onClick = {
                                    viewModel.selectItem.checkout()
                                }) {
                                    Text("立即支付")
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth().border(
                                    1.dp,
                                    color = Color.LightGray, shape = RoundedCornerShape(4.dp)
                                )
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {

                                    viewModel.selectItem.chosenShopItems.forEach {
                                        if (it.stock != 0) {
                                            key(it.uuid) {
                                                shopCheckListItem(it, viewModel)
                                            }
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
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Spacer(Modifier.height(80.dp))
                        pageTitle("购物", "选择商品")
                    }

                    item {
                        Spacer(Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .onPreviewKeyEvent { event: KeyEvent ->
                                    if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                        viewModel.selectItem.searchStoreItemSelect(keyword)
                                        true
                                    } else {
                                        false
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = keyword,
                                onValueChange = { keyword = it },
                                label = { Text("搜索商品（支持模糊搜索）") },
                                modifier = Modifier.padding(
                                    0.dp, 0.dp, 16.dp,
                                    0.dp
                                ).weight(1F)
                            )
                            Column {
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = {
                                    viewModel.selectItem.searchStoreItemSelect(keyword)
                                }, modifier = Modifier.height(56.dp)) {
                                    Icon(Icons.Default.Search, "")
                                }
                            }

                        }
                    }

                    if (searched) {
                            (0..<viewModel.selectItem.searchShopItems.size.floorDiv(3)).forEach {
                                item(it * 3) {
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Column {
                                            key(viewModel.selectItem.searchShopItems[it * 3].uuid) {
                                                shopItemCard(
                                                    viewModel.selectItem.searchShopItems[it * 3],
                                                    viewModel
                                                )
                                            }
                                        }
                                        Spacer(Modifier.weight(1F))
                                        Column {
                                            key(viewModel.selectItem.searchShopItems[it * 3 + 1].uuid){
                                            shopItemCard(
                                                viewModel.selectItem.searchShopItems[it * 3 + 1],
                                                viewModel
                                            )
                                        }
                                    }
                                        Spacer(Modifier.weight(1F))
                                        Column {
                                            key(viewModel.selectItem.searchShopItems[it * 3 + 2].uuid) {
                                                shopItemCard(
                                                    viewModel.selectItem.searchShopItems[it * 3 + 2],
                                                    viewModel
                                                )
                                            }
                                        }
                                    }

                                }
                            }

                            if (viewModel.selectItem.searchShopItems.size.mod(3) == 2) {
                                item(viewModel.selectItem.searchShopItems.size - 2) {
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Column {
                                            shopItemCard(
                                                viewModel.selectItem.searchShopItems[viewModel.selectItem.searchShopItems.size - 2],
                                                viewModel
                                            )
                                        }
                                        Spacer(Modifier.weight(1F))
                                        Column {
                                            shopItemCard(
                                                viewModel.selectItem.searchShopItems[viewModel.selectItem.searchShopItems.size - 1],
                                                viewModel
                                            )
                                        }
                                        Spacer(Modifier.weight(1F))
                                        Column {
                                            Spacer(Modifier.width(250.dp))
                                        }
                                    }
                                }
                            }

                            if (viewModel.selectItem.searchShopItems.size.mod(3) == 1) {
                                item(viewModel.selectItem.searchShopItems.size - 1) {
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Column {
                                            key(viewModel.selectItem.searchShopItems[viewModel.selectItem.searchShopItems.size - 1].uuid) {
                                                shopItemCard(
                                                    viewModel.selectItem.searchShopItems[viewModel.selectItem.searchShopItems.size - 1],
                                                    viewModel
                                                )
                                            }
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
                    } else {
                        (0..<viewModel.selectItem.totalShopItems.size.floorDiv(3)).forEach {
                            item(it * 3) {
                                Spacer(modifier = Modifier.height(20.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column {
                                        shopItemCard(
                                            viewModel.selectItem.totalShopItems[it * 3],
                                            viewModel
                                        )
                                    }
                                    Spacer(Modifier.weight(1F))
                                    Column {
                                        shopItemCard(
                                            viewModel.selectItem.totalShopItems[it * 3 + 1],
                                            viewModel
                                        )
                                    }
                                    Spacer(Modifier.weight(1F))
                                    Column {
                                        shopItemCard(
                                            viewModel.selectItem.totalShopItems[it * 3 + 2],
                                            viewModel
                                        )
                                    }
                                }
                            }
                        }

                        if (viewModel.selectItem.totalShopItems.size.mod(3) == 2) {
                            item(viewModel.selectItem.totalShopItems.size - 2) {
                                Spacer(modifier = Modifier.height(20.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column {
                                        shopItemCard(
                                            viewModel.selectItem.totalShopItems[viewModel.selectItem.totalShopItems.size - 2],
                                            viewModel
                                        )
                                    }
                                    Spacer(Modifier.weight(1F))
                                    Column {
                                        shopItemCard(
                                            viewModel.selectItem.totalShopItems[viewModel.selectItem.totalShopItems.size - 1],
                                            viewModel
                                        )
                                    }
                                    Spacer(Modifier.weight(1F))
                                    Column {
                                        Spacer(Modifier.width(250.dp))
                                    }
                                }
                            }
                        }

                        if (viewModel.selectItem.totalShopItems.size.mod(3) == 1) {
                            item(viewModel.selectItem.totalShopItems.size - 1) {
                                Spacer(modifier = Modifier.height(20.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column {
                                        shopItemCard(
                                            viewModel.selectItem.totalShopItems[viewModel.selectItem.totalShopItems.size - 1],
                                            viewModel
                                        )
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
}