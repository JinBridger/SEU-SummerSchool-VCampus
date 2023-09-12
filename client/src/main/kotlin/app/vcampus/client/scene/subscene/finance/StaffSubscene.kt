package app.vcampus.client.scene.subscene.finance

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.FinanceViewModel
import app.vcampus.server.enums.CardStatus

/**
 * staff subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun staffSubscene(viewModel: FinanceViewModel) {
    var cardNumber by viewModel.staff.cardNumber
    var rechargeAmount by viewModel.staff.rechargeAmount

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("一卡通管理", "充值 / 冻结 / 挂失")
                Spacer(Modifier.height(20.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .onPreviewKeyEvent { event: KeyEvent ->
                            if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                viewModel.staff.getByCardNumber()
                                true
                            } else {
                                false
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = { cardNumber = it },
                        label = { Text("输入一卡通号") },
                        modifier = Modifier.padding(
                            0.dp, 0.dp, 16.dp,
                            0.dp
                        ).weight(1F)
                    )
                    Column {
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
                            viewModel.staff.getByCardNumber()
                        }, modifier = Modifier.height(56.dp)) {
                            Icon(Icons.Default.Search, "")
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            if (viewModel.staff.searchedCard.value.cardNumber != 0) {
                item {
                    Row {
                        Surface(
                            modifier = Modifier.fillMaxWidth().border(
                                1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(4.dp)
                            ).padding(10.dp)
                        ) {
                            Column(Modifier.fillMaxWidth()) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            "卡号：${viewModel.staff.searchedCard.value.cardNumber}",
                                            fontWeight = FontWeight(700)
                                        )
                                        Spacer(Modifier.height(4.dp))
                                        Row {
                                            Text(
                                                "卡片状态：${viewModel.staff.searchedCard.value.status.label}",
                                                fontWeight = FontWeight(700)
                                            )
                                            Spacer(Modifier.width(10.dp))
                                            Text(
                                                "余额：${
                                                    String.format(
                                                        "%.2f",
                                                        viewModel.staff.searchedCard.value.balance / 100.0
                                                    )
                                                } 元",
                                                fontWeight = FontWeight(700)
                                            )
                                        }
                                    }
                                    Spacer(Modifier.weight(1F))
                                    when (viewModel.staff.searchedCard.value.status) {
                                        CardStatus.normal -> {
                                            Button(onClick = {
                                                viewModel.staff.freezeCard()
                                            }) {
                                                Text("冻结")
                                            }
                                            Spacer(Modifier.width(10.dp))
                                            Button(onClick = {
                                                viewModel.staff.reportLoss()
                                            }) {
                                                Text("挂失")
                                            }
                                        }

                                        CardStatus.frozen -> {
                                            Button(onClick = {
                                                viewModel.staff.unfreezeCard()
                                            }) {
                                                Text("解冻")
                                            }
                                        }

                                        CardStatus.lost -> {
                                            Button(onClick = {
                                                viewModel.staff.unfreezeCard()
                                            }) {
                                                Text("解挂")
                                            }
                                        }

                                        else -> {}
                                    }
                                }
                                Row(Modifier.fillMaxWidth()) {
                                    Row(Modifier.fillMaxWidth()) {
                                        OutlinedTextField(
                                            value = rechargeAmount,
                                            onValueChange = { rechargeAmount = it },
                                            label = { Text("充值金额") },
                                            modifier = Modifier.padding(
                                                0.dp, 0.dp, 16.dp,
                                                0.dp
                                            ).weight(1F)
                                        )
                                        Column {
                                            Spacer(Modifier.height(8.dp))
                                            Button(onClick = {
                                                viewModel.staff.rechargeCard()
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
            } else {
                item {
                    Text("未找到该卡片")
                }
            }
        }
    }
}