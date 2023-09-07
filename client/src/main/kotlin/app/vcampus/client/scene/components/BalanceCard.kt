package app.vcampus.client.scene.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun balanceCard() {
    Surface(modifier = Modifier.fillMaxWidth().border(
            1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
    )) {
        Row(Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("卡内余额", fontWeight = FontWeight(700))
                Spacer(Modifier.height(4.dp))
                Text("100.00 元", fontWeight = FontWeight(700), fontSize = 24.sp)
            }
            Spacer(Modifier.weight(1F))
            Button(onClick = {}) {
                Text("充值")
            }
            Spacer(Modifier.width(10.dp))
        }
    }
}