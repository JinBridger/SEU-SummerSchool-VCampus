package app.vcampus.client.scene.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.server.entity.FinanceCard

/**
 * balance card component, used in `MyBillsSubscene`
 *
 * @param card finance card, stores the data of card
 */
@Composable
fun balanceCard(card: FinanceCard) {
    val openPayDialog = remember { mutableStateOf(false) }

    if (openPayDialog.value) {
        AlertDialog(
                onDismissRequest = {
                    openPayDialog.value = false
                },
                title = {
                    Text(text = "充值请扫二维码关注东大财务公众号",
                            fontWeight = FontWeight(700))
                },
                text = {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(Modifier.weight(1F))
                        Image(painterResource("pay_qrcode.png"),
                                contentDescription = null,
                                modifier = Modifier.height(200.dp).width(
                                        200.dp))
                        Spacer(Modifier.weight(1F))
                    }
                },
                buttons = {
                    Row(
                            modifier = Modifier.padding(
                                    all = 8.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(Modifier.weight(1F))
                        TextButton(
                                onClick = { openPayDialog.value = false }
                        ) {
                            Text("确定")
                        }
                    }
                }
        )
    }

    Surface(
            modifier = Modifier.fillMaxWidth().border(
                    1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(4.dp)
            )
    ) {
        Row(Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("卡内余额", fontWeight = FontWeight(700))
                Spacer(Modifier.height(4.dp))
                Text(
                        String.format(
                                "%.2f",
                                card.balance / 100.0
                        ) + " 元", fontWeight = FontWeight(700),
                        fontSize = 24.sp
                )
            }
            Spacer(Modifier.weight(1F))
            Button(onClick = { openPayDialog.value = true }) {
                Text("充值")
            }
            Spacer(Modifier.width(10.dp))
        }
    }
}