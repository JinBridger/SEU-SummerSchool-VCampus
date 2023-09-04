package app.vcampus.client.scene.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun enterScoreListItem() {
    Surface(modifier = Modifier.fillMaxWidth().border(
            1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
    )) {
        Box(Modifier.fillMaxSize().padding(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Row(
                            verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.fillMaxHeight()) {
                            Row {
                                Text(
                                        text = "信号与系统",
                                        fontWeight = FontWeight(700)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                        text = "B09G1010",
                                        color = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text("1-16周 周一 1-13节 周二 1-13节 周三 1-13 节")
                            Text("九龙湖")
                        }
                    }
                }
                Spacer(Modifier.weight(1F))
                TextButton(onClick = {}) {
                    Text("下载导入模板")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = {}) {
                    Text("从文件导入成绩")
                }
            }
        }
    }
}