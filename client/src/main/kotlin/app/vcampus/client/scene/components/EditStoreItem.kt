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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.viewmodel.MutableStudent
import app.vcampus.client.repository._StoreItem
import app.vcampus.server.entity.StoreItem
import javax.swing.undo.StateEditable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditStoreItem(
    isEditable: Boolean = true,
    item: _StoreItem,
) {
    var myName by remember { mutableStateOf(item.itemName) }
    var myPrice by remember { mutableStateOf(item.price.toString()) }
    var myBarcode by remember { mutableStateOf(item.barcode) }
    var myStock by remember { mutableStateOf(item.stock.toString()) }
    var myDescription by remember { mutableStateOf(item.description) }

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
                        "索尼国行PS5主机PlayStation电视游戏机",
                        fontWeight = FontWeight(700),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    Text(
                        "279900元",
                        fontWeight = FontWeight(700),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        "库存剩余:10个",
                        fontWeight = FontWeight(700),
                        color = Color.DarkGray
                    )
                }
                if (expanded) {
                    Spacer(Modifier.height(6.dp))
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
                        Row{
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

                        if(isEditable){
                            if(!isEditing){
                                Row(modifier = Modifier.fillMaxWidth()){
                                    Spacer(Modifier.weight(1F))
                                    Button(onClick = {isEditing = true}){
                                        Row(verticalAlignment = Alignment.CenterVertically){
                                            Icon(Icons.Default.Edit,"")
                                            Spacer(Modifier.width(2.dp))
                                            Text("修改学生学籍信息")
                                        }
                                    }
                                }
                            }
                            else{
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Spacer(Modifier.weight(1F))
                                    Button(onClick = {

                                        isEditing = false
                                    }){
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Done, "")
                                            Spacer(Modifier.width(2.dp))
                                            Text("确认")
                                        }
                                    }
                                    Spacer(Modifier.width(8.dp))
                                    Button(onClick = {

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
}