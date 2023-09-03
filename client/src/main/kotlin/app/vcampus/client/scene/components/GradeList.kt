package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.viewmodel.TeachingAffairsViewModel
import app.vcampus.client.repository._GradeItem
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material.DataTable
import org.jetbrains.skia.paragraph.HeightMode
import javax.swing.undo.StateEditable



//@Composable
//fun GradeListItem(isEditable: Boolean = false,
//                  item:_GradeItem,
//                  viewModel: TeachingAffairsViewModel){
//    var expended by remember { mutableStateOf(false) }
//    Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(), verticalAlignment = Alignment.CenterVertically){
//        Text(
//            text = item.courseName,
//            fontWeight = FontWeight(700),
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(
//            text = item.courseId,
//            color = Color.DarkGray
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text("学分:${
//            String.format("%.2f",
//                item.credit / 1.0)
//        } ",
//            color = Color.LightGray)
//        Spacer(modifier = Modifier.weight(1F))
//        Text(
//            item.grade.toString() + " 分",
//            fontWeight = FontWeight(700),
////                    fontSize = 25.sp
//        )
//    }
//}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GradeListItem(
    item: _GradeItem,
    viewModel: TeachingAffairsViewModel
) {
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
                        Row{
                            Text(
                                text = item.courseName,
                                fontWeight = FontWeight(700),
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = item.courseId,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Spacer(Modifier.height(4.dp))
                        Row {
                            Chip(
                                onClick = {},
                                modifier = Modifier.height(22.dp),
                                border = BorderStroke(0.5.dp, Color.Black),
                                colors = ChipDefaults.chipColors(backgroundColor = Color.White),
                                content = { Text(
                                    "限选",
                                    color = Color.Black) }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Chip(
                                onClick = {},
                                modifier = Modifier.height(22.dp),
                                border = BorderStroke(0.5.dp, Color.Black),
                                colors = ChipDefaults.chipColors(backgroundColor = Color.White),
                                content = { Text(
                                    "学分:${
                                        String.format(
                                            "%.2f",
                                            item.credit / 1.0
                                        )
                                    } ",
                                    color = Color.Black
                                ) }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Chip(
                                onClick = {},
                                modifier = Modifier.height(22.dp),
                                border = BorderStroke(0.5.dp, Color.Black),
                                colors = ChipDefaults.chipColors(backgroundColor = Color.White),
                                content = { Text(
                                    "首修",
                                    color = Color.Black) }
                            )
                        }
                    }
//                    Text(
//                        "学分:${
//                            String.format(
//                                "%.2f",
//                                item.credit / 1.0
//                            )
//                        } ",
//                        color = Color.LightGray
//                    )
//                    SuggestionChip(
//                        onClick = {},
//                        modifier = Modifier.padding(4.dp),
//                        label = { Text(
//                            "限选",
//                            color = Color.Black) }
//                        )
//                    Chip(
//                        onClick = {},
//                        modifier = Modifier.padding(4.dp),
//                        border = BorderStroke(0.5.dp, Color.Black),
//                        colors = ChipDefaults.chipColors(backgroundColor = Color.White),
//                        content = { Text(
//                            "限选",
//                            color = Color.Black) }
//                        )
                    Spacer(modifier = Modifier.weight(1F))
                    Text(
                        item.grade.toString() + " 分",
                        fontWeight = FontWeight(700),
//                    fontSize = 25.sp
                    )
                }

                if (expanded) {
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
//                    Column(modifier = Modifier.fillMaxWidth()) {
//                        Row(modifier = Modifier.fillMaxWidth()) {
//                            Row {
//                                Text("平时成绩", fontWeight = FontWeight(700))
//                                Text("100")
//                            }
//                            Spacer(Modifier.width(4.dp))
//                            Row {
//                                Text("期中成绩", fontWeight = FontWeight(700))
//                                Text("100")
//                            }
//                            Spacer(Modifier.width(4.dp))
//                            Row {
//                                Text("期末成绩", fontWeight = FontWeight(700))
//                                Text("100")
//                            }
//                        }
//                        Spacer(Modifier.height(10.dp))
//                        Row {
//                            Divider()
//                            Text("课程成绩分布", fontWeight = FontWeight(700))
//                        }
//                    }

                    DataTable(
                        rowHeight = 40.dp,
                        headerHeight = 40.dp,
                        modifier = Modifier.fillMaxWidth(),
                        columns = listOf(
                            DataColumn(
                                width = TableColumnWidth.Flex(
                                    2F
                                )
                            ) {
                                Text(
                                    "平时分",
                                    fontWeight = FontWeight(
                                        700
                                    )
                                )
                            },
                            DataColumn(
                                width = TableColumnWidth.Flex(
                                    2F
                                )
                            ) {
                                Text(
                                    "期中",
                                    fontWeight = FontWeight(
                                        700
                                    )
                                )
                            },
                            DataColumn(
                                width = TableColumnWidth.Flex(
                                    2F
                                )
                            ) {
                                Text(
                                    "期末",
                                    fontWeight = FontWeight(
                                        700
                                    )
                                )
                            },
                            DataColumn(
                                width = TableColumnWidth.Flex(
                                    2F
                                )
                            ) {
                                Text(
                                    "最低分",
                                    fontWeight = FontWeight(
                                        700
                                    )
                                )
                            },
                            DataColumn(
                                width = TableColumnWidth.Flex(
                                    2F
                                )
                            ) {
                                Text(
                                    "平均分",
                                    fontWeight = FontWeight(
                                        700
                                    )
                                )
                            },
                            DataColumn(
                                width = TableColumnWidth.Flex(
                                    2F
                                )
                            ) {
                                Text(
                                    "最高分",
                                    fontWeight = FontWeight(
                                        700
                                    )
                                )
                            }

                        )
                    ) {
                        row {
                            cell { Text("100") }
                            cell { Text("100") }
                            cell { Text("100") }
                            cell { Text("100") }
                            cell { Text("100") }
                            cell { Text("100") }

                        }
                    }
                }
            }
        }
    }
    }
