package app.vcampus.client.scene.subscene.finance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.balanceCard
import app.vcampus.client.scene.components.billCard
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.FinanceViewModel

@Composable
fun myBillsSubscene(viewModel: FinanceViewModel) {
    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("一卡通", "查看一卡通余额与账单")
                Spacer(Modifier.height(20.dp))
            }

            item {
                balanceCard()
                Spacer(Modifier.height(20.dp))
                Text("账单", fontWeight = FontWeight(700), fontSize = 20.sp)
                Spacer(Modifier.height(10.dp))
            }

            item {
                Row(Modifier.fillMaxWidth()) {
                    Text("2023年9月7日", fontWeight = FontWeight(700))
                    Spacer(Modifier.weight(1F))
                    Text("支出：999.00元   收入：0.00元", fontWeight = FontWeight(700))
                }
            }

            (0..9).forEach {
                item {
                    Spacer(Modifier.height(10.dp))
                    billCard()
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}