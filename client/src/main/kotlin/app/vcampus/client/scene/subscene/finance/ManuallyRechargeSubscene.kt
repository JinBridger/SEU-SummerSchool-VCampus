package app.vcampus.client.scene.subscene.finance

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.FinanceViewModel

@Composable
fun manuallyRechargeSubscene(viewModel: FinanceViewModel) {
    val isExpanded = remember { mutableStateOf(false) }

    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("一卡通管理", "充值 / 冻结 / 挂失")
                Spacer(Modifier.height(20.dp))
            }

            item {
                Row(
                        modifier = Modifier.fillMaxWidth(),
//                                .onPreviewKeyEvent {event: KeyEvent ->
//                                    if(event.type== KeyEventType.KeyDown&&event.key== Key.Enter){
//                                        viewModel.modifyBook.searchBook()
//                                        true
//                                    }else{
//                                        false
//                                    }
//                                },
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                            value = "",
                            onValueChange = { },
                            label = { Text("输入一卡通号") },
                            modifier = Modifier.padding(
                                    0.dp, 0.dp, 16.dp,
                                    0.dp
                            ).weight(1F)
                    )
                    Column {
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
                            isExpanded.value = true
                        }, modifier = Modifier.height(56.dp)) {
                            Icon(Icons.Default.Search, "")
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            if (isExpanded.value) {
                item {
                    Row {
                        Surface(modifier = Modifier.fillMaxWidth().border(
                                1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(4.dp)
                        ).padding(10.dp)) {
                            Column(Modifier.fillMaxWidth()) {
                                Row(Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically) {
                                    Column {
                                        Text("卡号：212212212",
                                                fontWeight = FontWeight(700))
                                        Spacer(Modifier.height(4.dp))
                                        Row {
                                            Text("卡片状态：正常",
                                                    fontWeight = FontWeight(700))
                                            Spacer(Modifier.width(10.dp))
                                            Text("余额：100.00 元",
                                                    fontWeight = FontWeight(700))
                                        }
                                    }
                                    Spacer(Modifier.weight(1F))
                                    Button(onClick = {}) {
                                        Text("冻结")
                                    }
                                    Spacer(Modifier.width(10.dp))
                                    Button(onClick = {}) {
                                        Text("挂失")
                                    }
                                }
                                Row(Modifier.fillMaxWidth()) {
                                    Row(Modifier.fillMaxWidth()) {
                                        OutlinedTextField(
                                                value = "",
                                                onValueChange = { },
                                                label = { Text("充值金额") },
                                                modifier = Modifier.padding(
                                                        0.dp, 0.dp, 16.dp,
                                                        0.dp
                                                ).weight(1F)
                                        )
                                        Column {
                                            Spacer(Modifier.height(8.dp))
                                            Button(onClick = {
                                            }, modifier = Modifier.height(56.dp)) {
                                                Text("充值")
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
}