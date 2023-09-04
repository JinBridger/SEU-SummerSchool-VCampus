package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun selectClassCard(isConflict: Boolean = false, isChosen: Boolean = false) {
    Surface(modifier = Modifier.border(
            1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
    ).width(250.dp).padding(10.dp)) {
        Column {
            Row(Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                Text("王世杰", fontWeight = FontWeight(700))
                Spacer(Modifier.width(16.dp))
                Column {
                    if (isChosen) {
                        Chip(
                                onClick = {},
                                modifier = Modifier.height(22.dp),
                                border = ChipDefaults.outlinedBorder,
                                colors = ChipDefaults.outlinedChipColors(
                                        Color(0xff508e54)),
                                content = {
                                    Text(
                                            "已选",
                                            color = Color.White)
                                }
                        )
                    } else {
                        if (isConflict) {
                            Chip(
                                    onClick = {},
                                    modifier = Modifier.height(22.dp),
                                    border = ChipDefaults.outlinedBorder,
                                    colors = ChipDefaults.outlinedChipColors(
                                            Color(0xffe74c3c)),
                                    content = {
                                        Text(
                                                "课程冲突",
                                                color = Color.White)
                                    }
                            )
                        }
                    }
                    Spacer(Modifier.height(2.dp))
                }
            }
            Spacer(Modifier.height(10.dp))
            Text("1-16周 周一 1-13节 周二 1-13节 周三 1-13 节")
            Text("九龙湖")
            Spacer(Modifier.height(10.dp))
            Row {
                Text("教学班容量：", fontWeight = FontWeight(700))
                Text("99人")
            }
            Row {
                Text("已选人数：", fontWeight = FontWeight(700))
                Text("99人")
            }
            Spacer(Modifier.weight(1F))
            Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                if(isChosen) {
                    Button(onClick = {}) {
                        Text("退选")
                    }
                } else {
                    Button(onClick = {}, enabled = !isConflict) {
                        Text("选择")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun selectClassListItem(isChosen: Boolean = false) {
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
                                    fontWeight = FontWeight(700),
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                    text = "B09G1010",
                                    color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Spacer(Modifier.height(4.dp))
                        Row {
                            Chip(
                                    onClick = {},
                                    modifier = Modifier.height(22.dp),
                                    border = ChipDefaults.outlinedBorder,
                                    colors = ChipDefaults.outlinedChipColors(
                                            Color(0xff16a085)),
                                    content = {
                                        Text(
                                                "限选",
                                                color = Color.White)
                                    }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Chip(
                                    onClick = {},
                                    modifier = Modifier.height(22.dp),
                                    border = ChipDefaults.outlinedBorder,
                                    colors = ChipDefaults.outlinedChipColors(
                                            Color(0xff3498db)),
                                    content = {
                                        Text(
                                                "学分:${
                                                    String.format(
                                                            "%.2f",
                                                            4.0 / 1.0
                                                    )
                                                } ",
                                                color = Color.White
                                        )
                                    }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Chip(
                                    onClick = {},
                                    modifier = Modifier.height(22.dp),
                                    border = ChipDefaults.outlinedBorder,
                                    colors = ChipDefaults.chipColors(
                                            Color(0xff34495e)),
                                    content = {
                                        Text(
                                                "首修",
                                                color = Color.White)
                                    }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    if(isChosen) {
                        Icon(Icons.Default.Done, "")
                        Spacer(Modifier.width(4.dp))
                        Text(
                                "已选",
                                fontWeight = FontWeight(700),
                        )
                    }
                }

                if (expanded) {
                    val state = rememberLazyListState()
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                    LazyRow(Modifier.fillMaxWidth(), state) {
                        (0..8).forEach {
                            item {
                                selectClassCard(true, true)
                                Spacer(Modifier.width(10.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    HorizontalScrollbar(
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 12.dp),
                            adapter = rememberScrollbarAdapter(
                                    scrollState = state
                            )
                    )
                }
            }
        }
    }
}