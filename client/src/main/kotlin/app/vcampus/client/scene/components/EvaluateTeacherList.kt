package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
fun ratingBar() {
    Column(Modifier.fillMaxWidth()) {
        Text("教学态度与课前准备：师德师风、备课情况、教材选用",
                fontWeight = FontWeight(700))
        Row(Modifier.fillMaxWidth()) {
            (0..10).forEach {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Checkbox(
                            checked = false,
                            onCheckedChange = { }
                    )
                    Text(it.toString())
                }
            }
        }
        Spacer(Modifier.height(10.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun evaluateTeacherListItem() {
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
                                    text = "王世杰",
                                    fontWeight = FontWeight(700),
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Row {
                            Text(
                                    text = "信号与系统",
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                    text = "B09G1010",
                                    color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    Text(
                            "待评教",
                            fontWeight = FontWeight(700),
                    )
                }

                if (expanded) {
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                    (0..5).forEach {
                        ratingBar()
                    }
                    Text("其他想要评价的内容：", fontWeight = FontWeight(700))
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField("", onValueChange = {}, maxLines = 5,
                            modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(Modifier.weight(1F))
                        Button(onClick = {}) {
                            Text("提交")
                        }
                    }
                }
            }
        }
    }
}
