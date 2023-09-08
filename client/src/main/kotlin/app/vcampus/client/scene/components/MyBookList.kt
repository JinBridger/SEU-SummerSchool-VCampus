package app.vcampus.client.scene.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun myBookList() {
    Surface(modifier = Modifier.fillMaxWidth().border(
            1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
    ).padding(10.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Column {
                Text("奥本海默传", fontWeight = FontWeight(700))
                Text("条码号：1111111111")
                Text("馆藏地：工业技术图书阅览室(九龙湖A401)")
                Text("借阅日期：2023年9月8日")
                Text("应还日期：2023年10月8日")
            }
            Spacer(Modifier.weight(1F))
            Button(onClick = {}) {
                Text("续借")
            }
        }
    }
}