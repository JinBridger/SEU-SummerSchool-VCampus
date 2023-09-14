package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.vcampus.server.entity.StoreItem

/**
 * edit store item component, used in `ModifyItemSubscene`
 *
 * @param storeItem StoreItem of the list item
 * @param isEditable whether it could be edited
 * @param onEdit function when edit the item
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditStoreItem(
    storeItem: StoreItem,
    isEditable: Boolean = false,
    onEdit: (StoreItem) -> (Unit) = { _: StoreItem -> }
) {
    var myPrice by remember { mutableStateOf(String.format("%.2f", storeItem.price.toInt() / 100.0)) }
    var myStock by remember { mutableStateOf(storeItem.stock.toString()) }
    var myBarcode by remember { mutableStateOf(storeItem.barcode) }
    var myName by remember { mutableStateOf(storeItem.itemName) }
    var myPictureLink by remember { mutableStateOf(storeItem.pictureLink) }
    var myDescription by remember { mutableStateOf(storeItem.description) }

    var expanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    Surface(modifier = Modifier.fillMaxWidth().border(
        1.dp,
        color = Color.LightGray,
        shape = RoundedCornerShape(4.dp)
    ).animateContentSize(
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    ), onClick = { expanded = !expanded }) {
        Box(Modifier.fillMaxWidth().fillMaxHeight().padding(10.dp)) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        myName,
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                        modifier = Modifier.weight(2.5F),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(0.1F))
                    Text(
                        "价格：$myPrice￥ ",
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                        modifier = Modifier.weight(1F),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        "库存剩余：${myStock}个",
                        fontWeight = FontWeight(700),
                        color = Color.DarkGray,
                        modifier = Modifier.weight(1F),
                        maxLines = 1
                    )
                }
                if (expanded) {
                    if (!isEditing) {
                        Spacer(Modifier.height(20.dp))
                        Divider()
                        Spacer(Modifier.height(6.dp))
                        Column {
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = myName,
                                    onValueChange = { myName = it },
                                    label = { Text("商品名称") },
                                    isError = myName == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myName == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.2F),
                                    value = myPrice,
                                    onValueChange = { myPrice = it },
                                    label = { Text("商品价格(￥)") },
                                    isError = myPrice == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myPrice == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.2F),
                                    value = myStock,
                                    onValueChange = { myStock = it },
                                    label = { Text("商品库存") },
                                    isError = myStock == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myStock == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.6F),
                                    value = myBarcode,
                                    onValueChange = { myBarcode = it },
                                    label = { Text("商品条形码") },
                                    isError = myBarcode == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myBarcode == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(1F),
                                    value = myPictureLink,
                                    onValueChange = { myPictureLink = it },
                                    label = { Text("商品图片链接") },
                                    isError = myPictureLink == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myPictureLink == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(1F).height(120.dp),
                                    value = myDescription,
                                    onValueChange = { myDescription = it },
                                    label = { Text("商品介绍") },
                                    isError = myDescription == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myDescription == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))

                            if (isEditable) {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Spacer(Modifier.weight(1F))
                                    Button(onClick = { isEditing = true }) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Edit, "")
                                            Spacer(Modifier.width(2.dp))
                                            Text("修改商品相关信息")
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Spacer(Modifier.height(20.dp))
                        Divider()
                        Spacer(Modifier.height(6.dp))
                        Column {
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = myName,
                                    onValueChange = { myName = it },
                                    label = { Text("商品名称") },
                                    isError = myName == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myName == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.2F),
                                    value = myPrice,
                                    onValueChange = { myPrice = it },
                                    label = { Text("商品价格(￥)") },
                                    isError = myPrice == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myPrice == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.2F),
                                    value = myStock,
                                    onValueChange = { myStock = it },
                                    label = { Text("商品库存") },
                                    isError = myStock == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myStock == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.6F),
                                    value = myBarcode,
                                    onValueChange = { myBarcode = it },
                                    label = { Text("商品条形码") },
                                    isError = myBarcode == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myBarcode == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(1F),
                                    value = myPictureLink,
                                    onValueChange = { myPictureLink = it },
                                    label = { Text("商品图片链接") },
                                    isError = myPictureLink == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myPictureLink == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(1F).height(120.dp),
                                    value = myDescription,
                                    onValueChange = { myDescription = it },
                                    label = { Text("商品介绍") },
                                    isError = myDescription == "",
                                    readOnly = !isEditing,
                                    trailingIcon = {
                                        if (myDescription == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        if (isEditing) {
                            Spacer(Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(Modifier.weight(1F))
                                Button(
                                    onClick = {
                                        val modifiedStoreItem = StoreItem()
                                        modifiedStoreItem.uuid = storeItem.uuid
                                        modifiedStoreItem.barcode = myBarcode
                                        modifiedStoreItem.itemName = myName
                                        modifiedStoreItem.stock = myStock.toInt()
                                        modifiedStoreItem.description = myDescription
                                        modifiedStoreItem.price = (myPrice.toDouble() * 100).toInt()
                                        modifiedStoreItem.pictureLink = myPictureLink
                                        onEdit(modifiedStoreItem)

                                        isEditing = false
                                        myPrice = String.format("%.2f", (myPrice.toDouble() * 100).toInt() / 100.0)
                                    }
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Done, "")
                                        Spacer(Modifier.width(2.dp))
                                        Text("确认")
                                    }
                                }
                                Spacer(Modifier.width(8.dp))
                                Button(onClick = {
                                    myName = storeItem.itemName
                                    myBarcode = storeItem.barcode
                                    myStock = storeItem.stock.toString()
                                    myBarcode = storeItem.barcode
                                    myDescription = storeItem.description
                                    myPrice = String.format("%.2f", myPrice.toInt() / 100.0)
                                    myPictureLink = storeItem.pictureLink.toString()

                                    isEditing = false
                                }) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Close, "")
                                        Spacer(Modifier.width(2.dp))
                                        Text("取消")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}