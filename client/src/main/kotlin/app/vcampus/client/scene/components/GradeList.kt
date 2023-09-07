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
import app.vcampus.server.entity.TeachingClass
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material.DataTable


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
    item: TeachingClass
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
                        Row {
                            Text(
                                text = item.courseName,
                                fontWeight = FontWeight(700),
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = item.course.courseId,
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
                                colors = ChipDefaults.outlinedChipColors(Color(0xff16a085)),
                                content = {
                                    Text(
                                        "限选",
                                        color = Color.White
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Chip(
                                onClick = {},
                                modifier = Modifier.height(22.dp),
                                border = ChipDefaults.outlinedBorder,
                                colors = ChipDefaults.outlinedChipColors(Color(0xff3498db)),
                                content = {
                                    Text(
                                        "${
                                            String.format(
                                                "%.2f",
                                                item.course.credit / 1.0
                                            )
                                        } 学分",
                                        color = Color.White
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Chip(
                                onClick = {},
                                modifier = Modifier.height(22.dp),
                                border = ChipDefaults.outlinedBorder,
                                colors = ChipDefaults.chipColors(Color(0xff34495e)),
                                content = {
                                    Text(
                                        "首修",
                                        color = Color.White
                                    )
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    Text(
                        item.selectedClass.grade.total.toString() + " 分",
                        fontWeight = FontWeight(700),
                    )
                }

                if (expanded) {
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    DataTable(
                        rowHeight = 40.dp,
                        headerHeight = 40.dp,
                        modifier = Modifier.fillMaxWidth(),
                        columns = listOf(
                            DataColumn(
                                alignment = Alignment.CenterHorizontally,
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
                                alignment = Alignment.CenterHorizontally,
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
                                alignment = Alignment.CenterHorizontally,
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
                                alignment = Alignment.CenterHorizontally,
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
                                alignment = Alignment.CenterHorizontally,
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
                                alignment = Alignment.CenterHorizontally,
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
                            cell { Text(item.selectedClass.grade.general.toString()) }
                            cell { Text(item.selectedClass.grade.midterm.toString()) }
                            cell { Text(item.selectedClass.grade.finalExam.toString()) }
                            cell { Text(item.selectedClass.grade.classMin.toString()) }
                            cell {
                                Text(
                                    String.format(
                                        "%.2f",
                                        item.selectedClass.grade.classAvg
                                    )
                                )
                            }
                            cell { Text(item.selectedClass.grade.classMax.toString()) }
                        }
                    }
                }
            }
        }
    }
}
