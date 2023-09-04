package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun ratingResultBar() {
    val colors = listOf(Color(0xffd65745), Color(0xffc45c24), Color(0xffd8833b),
            Color(0xffe7a03c), Color(0xffeac645), Color(0xff65c97a),
            Color(0xff55ac68), Color(0xff58b99d), Color(0xff4a9e86),
            Color(0xff326b5a))
    Column(Modifier.fillMaxWidth()) {
        Text("教学态度与课前准备：师德师风、备课情况、教材选用 (颜色占比代表打分学生数量)",
                fontWeight = FontWeight(700))
        Spacer(Modifier.height(4.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("0")
            Spacer(Modifier.width(10.dp))
            (0..9).forEach {
                Box(Modifier.weight(1F).background(colors[it]).height(10.dp))
            }
            Spacer(Modifier.width(10.dp))
            Text("10")
        }
        Spacer(Modifier.height(10.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun evaluateResultListItem() {
    var expanded by remember { mutableStateOf(false) }
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
        Box(Modifier.fillMaxSize().padding(10.dp)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
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
                    }
                }

                if (expanded) {
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                    (0..5).forEach {
                        ratingResultBar()
                    }
                }
            }
        }
    }
}