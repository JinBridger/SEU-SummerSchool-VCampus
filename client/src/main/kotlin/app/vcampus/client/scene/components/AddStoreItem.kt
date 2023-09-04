package app.vcampus.client.scene.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.client.repository._StoreItem
import app.vcampus.client.viewmodel.ShopViewModel

@Composable
fun addShopItem(item: _StoreItem, viewModel: ShopViewModel,isEditable: Boolean = true) {
    var myName by remember { mutableStateOf(item.itemName) }
    var myPrice by remember { mutableStateOf(item.price.toString())}
    var myBarcode by remember { mutableStateOf(item.barcode) }
    var myStock by remember { mutableStateOf(item.stock.toString()) }
    var myDescription by remember { mutableStateOf(item.description) }


    Box(modifier = Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
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
                            value =  myName,
                            onValueChange = {  myName = it },
                            label = { Text("请填写您的商品名称") },
                            isError =  myName == "",
                            trailingIcon = {
                                if ( myName == "") Icon(
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
                            value =  myPrice,
                            onValueChange = {  myPrice = it },
                            label = { Text("请填写商品价格(单位：￥)") },
                            isError =  myPrice == " ",
                            trailingIcon = {
                                if ( myPrice == "") Icon(
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
                            value =  myStock,
                            onValueChange = {  myStock = it},
                            label = { Text("请输入你要添加的商品数量") },
                            isError =  myStock =="",
                            trailingIcon = {
                                if ( myStock == "") Icon(
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
                            value =  myBarcode,
                            onValueChange = {  myBarcode = it },
                            label = { Text("请填写您要上传的商品的条形码") },
                            isError =  myBarcode == "",
                            trailingIcon = {
                                if ( myBarcode == "") Icon(
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
                            "商品介绍",
                            fontWeight = FontWeight(700)
                        )
                        Spacer(modifier = Modifier.width(25.dp))
                        OutlinedTextField(
                            readOnly = false,
                            value =  myDescription,
                            onValueChange = {  myDescription = it },
                            label = { Text("请介绍一下您的商品信息") },
                            isError =  myDescription == "",
                            trailingIcon = {
                                if ( myDescription == "") Icon(
                                    Icons.Filled.Error,
                                    "error",
                                    tint = MaterialTheme.colors.error
                                )
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                Spacer(Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(Modifier.weight(1F))
                        Button(onClick = { }) {
                            Text("添加商品")
                        }
                    }
                }
            }
        }
    }