package app.vcampus.client.scene.subscene.finance

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.balanceCard
import app.vcampus.client.scene.components.billCard
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.FinanceViewModel
import app.vcampus.server.utility.DateUtility

/**
 * my bills subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun myBillsSubscene(viewModel: FinanceViewModel) {
    LaunchedEffect(Unit) {
        viewModel.mybills.init()
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("一卡通", "查看一卡通余额与账单")
                Spacer(Modifier.height(20.dp))
            }

            item {
                balanceCard(viewModel.mybills.myCard.value)
                Spacer(Modifier.height(20.dp))
                Text("账单", fontWeight = FontWeight(700), fontSize = 20.sp)
            }

            val currentDate = mutableStateOf("")

            viewModel.mybills.myBills.forEach { bill ->
                if (DateUtility.fromDate(bill.time) != currentDate.value) {
                    item {
                        Spacer(Modifier.height(10.dp))
                        Row(Modifier.fillMaxWidth()) {
                            Text(DateUtility.fromDate(bill.time, "yyyy年MM月dd日"), fontWeight = FontWeight(700))
                            Spacer(Modifier.weight(1F))
                        }
                    }
                    currentDate.value = DateUtility.fromDate(bill.time)
                }
                item {
                    Spacer(Modifier.height(10.dp))
                    billCard(bill)
                }
            }
            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}