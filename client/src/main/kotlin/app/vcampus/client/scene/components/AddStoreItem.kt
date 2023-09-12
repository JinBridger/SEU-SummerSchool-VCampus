package app.vcampus.client.scene.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.client.viewmodel.ShopViewModel
import app.vcampus.server.entity.StoreItem
import kotlinx.coroutines.launch

/**
 * add shop item component, used in `AddItemSubscene`
 *
 * @param viewModel the view model of the subscene
 */
@Composable
fun addShopItem(viewModel: ShopViewModel) {

    var myPrice by remember { mutableStateOf("") }
    var myName by remember { mutableStateOf("") }
    var myStock by remember { mutableStateOf("") }
    var myBarcode by remember { mutableStateOf("") }
    var myPictureLink by remember { mutableStateOf("") }
    var myDescription by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    "商品名称",
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.width(25.dp))
                OutlinedTextField(
                    readOnly = false,
                    value = myName,
                    onValueChange = { myName = it },
                    label = { Text("请填写您的商品名称") },
                    isError = myName == "",
                    trailingIcon = {
                        if (myName == "") Icon(
                            Icons.Filled.Error,
                            "error",
                            tint = MaterialTheme.colors.error
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    "商品价格",
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.width(25.dp))
                OutlinedTextField(
                    readOnly = false,
                    value = myPrice,
                    onValueChange = { myPrice = it },
                    label = { Text("请填写商品价格(单位：￥)") },
                    isError = myPrice == "",
                    trailingIcon = {
                        if (myPrice == "") Icon(
                            Icons.Filled.Error,
                            "error",
                            tint = MaterialTheme.colors.error
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    "商品份数",
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.width(25.dp))
                OutlinedTextField(
                    readOnly = false,
                    value = myStock,
                    onValueChange = { myStock = it },
                    label = { Text("请输入你要添加的商品数量") },
                    isError = myStock == "",
                    trailingIcon = {
                        if (myStock == "") Icon(
                            Icons.Filled.Error,
                            "error",
                            tint = MaterialTheme.colors.error
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(0.dp))
                Text(
                    "商品条形码",
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.width(25.dp))
                OutlinedTextField(
                    readOnly = false,
                    value = myBarcode,
                    onValueChange = { myBarcode = it },
                    label = { Text("请填写您要上传的商品的条形码") },
                    isError = myBarcode == "",
                    trailingIcon = {
                        if (myBarcode == "") Icon(
                            Icons.Filled.Error,
                            "error",
                            tint = MaterialTheme.colors.error
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    "图片链接",
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.width(25.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(1F),
                    value = myPictureLink,
                    onValueChange = { myPictureLink = it },
                    label = { Text("商品图片链接") },
                    isError = myPictureLink == "",
                    readOnly = false,
                    trailingIcon = {
                        if (myPictureLink == "") Icon(
                            Icons.Filled.Error, "error",
                            tint = MaterialTheme.colors.error
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    "商品介绍",
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.width(25.dp))
                OutlinedTextField(
                    readOnly = false,
                    value = myDescription,
                    onValueChange = { myDescription = it },
                    label = { Text("请介绍一下您的商品信息") },
                    isError = myDescription == "",
                    trailingIcon = {
                        if (myDescription == "") Icon(
                            Icons.Filled.Error,
                            "error",
                            tint = MaterialTheme.colors.error
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(120.dp)
                )

            }
            Spacer(Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.weight(1F))
                Button(onClick = {
                    try {
                        val newItem = StoreItem()
                        newItem.itemName = myName
                        newItem.price = (myPrice.toDouble() * 100).toInt()
                        newItem.stock = myStock.toInt()
                        newItem.barcode = myBarcode
                        newItem.pictureLink = myPictureLink
                        newItem.description = myDescription

                        viewModel.addStoreItem.addStoreItem(newItem)
                    } catch (
                        e: Exception
                    ) {
                        println(e)
                        viewModel.addStoreItem.result.value = false
                        viewModel.addStoreItem.showMessage.value = true
                    }

                }) {
                    Text("添加商品")
                }
            }

        }

        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            val state = remember {
                SnackbarHostState()
            }
            SnackbarHost(hostState = state)

            Crossfade(viewModel.addStoreItem.showMessage) {
                if (it.value) {
                    if (viewModel.addStoreItem.result.value) {
                        scope.launch {
                            state.showSnackbar(
                                "已成功添加商品", "关闭"
                            )
                        }
                    } else {
                        scope.launch {
                            state.showSnackbar(
                                "添加商品失败", "关闭"
                            )
                        }
                    }

                    viewModel.addStoreItem.showMessage.value = false
                }
            }
        }
    }

}
