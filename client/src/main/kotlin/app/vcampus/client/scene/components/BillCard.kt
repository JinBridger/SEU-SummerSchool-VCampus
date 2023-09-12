package app.vcampus.client.scene.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.server.entity.CardTransaction
import app.vcampus.server.utility.DateUtility

/**
 * bill card component, used in `MyBillsSubscene`
 *
 * @param bill card transaction, stores the information of transaction
 */
@Composable
fun billCard(bill: CardTransaction) {
    Surface(
        modifier = Modifier.fillMaxWidth().border(
            1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        Row(
            Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(10.dp))
            Icon(Icons.Default.LocalMall, "", tint = Color(0xff66626b))
            Spacer(Modifier.width(20.dp))
            Column {
                Text(bill.description, fontWeight = FontWeight(700))
                Spacer(Modifier.height(4.dp))
                Text(DateUtility.fromDate(bill.time, "yyyy年MM月dd日 HH:mm:ss"))
            }
            Spacer(Modifier.weight(1F))
            Text(
                String.format(
                    "%.2f",
                    bill.amount / 100.0
                ) + " 元",
                fontWeight = FontWeight(700),
                color = Color(bill.type.color)
            )
            Spacer(Modifier.width(10.dp))
        }
    }
}