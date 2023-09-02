package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material.DataTable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun searchBookListItem(isEditable: Boolean = false) {
    var expanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    Surface(modifier = Modifier.fillMaxWidth().border(1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)).animateContentSize(
            animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
            )
    ), onClick = { expanded = !expanded }) {
        Box(Modifier.fillMaxSize().padding(10.dp)) {
            Column {
                Row {
                    Column {
                        Text("C++ Primer 中文版", fontWeight = FontWeight(700))
                        Text("Stanley B. Lippman, Josée Lajoie, Barbara E. Moo著")
                        Spacer(Modifier.height(4.dp))
                        Text("电子工业出版社 2013", fontSize = 14.sp)
                    }
                    Spacer(Modifier.weight(1F))
                    Column {
                        Text("馆藏副本：10", fontSize = 12.sp,
                                fontWeight = FontWeight(700),
                                color = Color.Gray)
                        Text("可借副本：10", fontSize = 12.sp,
                                fontWeight = FontWeight(700),
                                color = Color.Gray)
                    }
                }



                if (expanded) {
                    if (!isEditing) {
                        Spacer(Modifier.height(6.dp))
                        Divider()
                        Spacer(Modifier.height(6.dp))
                        Row(Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.fillMaxWidth(0.8F)) {
                                Row {
                                    Text("ISBN: ", fontWeight = FontWeight(700))
                                    Text("978-7-121-15535-2")
                                }
                                Spacer(Modifier.height(4.dp))
                                Row {
                                    Text("简介: ", fontWeight = FontWeight(700))
                                    Text("这本久负盛名的 C++ 经典教程，时隔八年之久，终迎来史无前例的重大升级。除令全球无数程序员从中受益，甚至为之迷醉的——C++ 大师 Stanley B. Lippman 的丰富实践经验，C++标准委员会原负责人 Josée Lajoie 对C++标准的深入理解，以及C+ + 先驱 Barbara E. Moo 在 C++教学方面的真知灼见外，更是基于全新的 C++11标准进行了全面而彻底的内容更新。非常难能可贵的是，本书所有示例均全部采用 C++11 标准改写，这在经典升级版中极其罕见——充分体现了 C++ 语言的重大进展及其全面实践。书中丰富的教学辅助内容、醒目的知识点提示，以及精心组织的编程示范，让这本书在 C++ 领域的权威地位更加不可动摇。无论是初学者入门，或是中、高级程序员提升，本书均为不容置疑的首选。")
                                }
                                Spacer(Modifier.height(4.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Box(modifier = Modifier.fillMaxWidth().aspectRatio(
                                        0.7F).clip(
                                        RoundedCornerShape(4.dp)).background(
                                        Color.Cyan)) {
                                    Image(painterResource("test_image.png"), "",
                                            contentScale = ContentScale.FillBounds)
                                }
                                Spacer(Modifier.weight(1F))
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Divider()
                        DataTable(
                                rowHeight = 40.dp,
                                headerHeight = 40.dp,
                                modifier = Modifier.fillMaxWidth(),
                                columns = listOf(
                                        DataColumn(
                                                width = TableColumnWidth.Flex(
                                                        2F)) {
                                            Text("索书号",
                                                    fontWeight = FontWeight(
                                                            700))
                                        },
                                        DataColumn(
                                                width = TableColumnWidth.Flex(
                                                        2F)) {
                                            Text("条形码",
                                                    fontWeight = FontWeight(
                                                            700))
                                        },
                                        DataColumn(
                                                width = TableColumnWidth.Flex(
                                                        6F)) {
                                            Text("馆藏地",
                                                    fontWeight = FontWeight(
                                                            700))
                                        },
                                        DataColumn(
                                                width = TableColumnWidth.Flex(
                                                        1F)) {
                                            Text("书籍状态",
                                                    fontWeight = FontWeight(
                                                            700))
                                        },
                                )
                        ) {
                            row {
                                cell { Text("TP312C/98/.4") }
                                cell { Text("2847857") }
                                cell { Text("工业技术图书阅览室（九龙湖A401）") }
                                cell { Text("可借", color = Color(0xff508e54)) }
                            }
                            row {
                                cell { Text("TP312C/98/.4") }
                                cell { Text("2847859") }
                                cell { Text("工业技术图书阅览室（九龙湖A401）") }
                                cell { Text("可借", color = Color(0xff508e54)) }
                            }
                        }

                        if (isEditable) {
                            Row(Modifier.fillMaxWidth()) {
                                Spacer(Modifier.weight(1F))
                                Button(onClick = { isEditing = true }) {
                                    Text("修改图书信息")
                                }
                            }
                        }
                    } else {
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                    value = "",
                                    onValueChange = { },
                                    label = { Text("书名") },
                                    modifier = Modifier.padding(
                                            0.dp, 0.dp, 16.dp,
                                            0.dp
                                    ).weight(1F)
                            )
                            OutlinedTextField(
                                    value = "",
                                    onValueChange = { },
                                    label = { Text("作者") },
                                    modifier = Modifier.padding(
                                            0.dp, 0.dp, 16.dp,
                                            0.dp
                                    ).weight(1F)
                            )
                            OutlinedTextField(
                                    value = "",
                                    onValueChange = { },
                                    label = { Text("出版社") },
                                    modifier = Modifier.padding(
                                            0.dp, 0.dp, 0.dp,
                                            0.dp
                                    ).weight(1F)
                            )
                        }
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                    value = "",
                                    onValueChange = { },
                                    label = { Text("封面图片链接") },
                                    modifier = Modifier.padding(
                                            0.dp, 0.dp, 16.dp,
                                            0.dp
                                    ).weight(2F)
                            )
                            OutlinedTextField(
                                    value = "",
                                    onValueChange = { },
                                    label = { Text("ISBN") },
                                    modifier = Modifier.padding(
                                            0.dp, 0.dp, 0.dp,
                                            0.dp
                                    ).weight(1F)
                            )
                        }
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                    value = "",
                                    onValueChange = { },
                                    label = { Text("简介") },
                                    modifier = Modifier.padding(
                                            0.dp, 0.dp, 0.dp,
                                            0.dp
                                    ).weight(1F)
                            )
                        }

                        DataTable(
                                modifier = Modifier.fillMaxWidth(),
                                columns = listOf(
                                        DataColumn(
                                                width = TableColumnWidth.Flex(
                                                        2F)) {
                                            Text("索书号",
                                                    fontWeight = FontWeight(
                                                            700))
                                        },
                                        DataColumn(
                                                width = TableColumnWidth.Flex(
                                                        2F)) {
                                            Text("条形码",
                                                    fontWeight = FontWeight(
                                                            700))
                                        },
                                        DataColumn(
                                                width = TableColumnWidth.Flex(
                                                        6F)) {
                                            Text("馆藏地",
                                                    fontWeight = FontWeight(
                                                            700))
                                        },
                                        DataColumn(
                                                width = TableColumnWidth.Flex(
                                                        1F)) {
                                            Text("书籍状态",
                                                    fontWeight = FontWeight(
                                                            700))
                                        },
                                        DataColumn(
                                                width = TableColumnWidth.Flex(
                                                        1F)) {
                                            Text("操作",
                                                    fontWeight = FontWeight(
                                                            700))
                                        }
                                )
                        ) {
                            row {
                                cell {
                                    Box(
                                            modifier = Modifier.fillMaxSize(0.9F).border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)).padding(4.dp)
                                    ) {
                                        BasicTextField(
                                                value = "",
                                                onValueChange = { },
                                        )
                                    }
                                }
                                cell {
                                    Box(
                                            modifier = Modifier.fillMaxSize(0.9F).border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)).padding(4.dp)
                                    ) {
                                        BasicTextField(
                                                value = "",
                                                onValueChange = { },
                                        )
                                    }
                                }
                                cell {
                                    Box(
                                            modifier = Modifier.fillMaxSize(0.9F).border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)).padding(4.dp)
                                    ) {
                                        BasicTextField(
                                                value = "",
                                                onValueChange = { },
                                        )
                                    }
                                }
                                cell {
                                    Box(
                                            modifier = Modifier.fillMaxSize(0.9F).border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)).padding(4.dp)
                                    ) {
                                        BasicTextField(
                                                value = "",
                                                onValueChange = { },
                                        )
                                    }
                                }
                                cell {
                                    TextButton(onClick = {}) {
                                        Text("删除图书")
                                    }
                                }
                            }
                        }

                        if(isEditing) {
                            Spacer(Modifier.height(10.dp))
                            Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(Modifier.weight(1F))
                                Button(onClick = {
                                    isEditing = false
                                }) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Done, "")
                                        Spacer(Modifier.width(2.dp))
                                        Text("确认")
                                    }
                                }
                                Spacer(Modifier.width(8.dp))
                                Button(onClick = {
                                    isEditing = false
                                }) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Close, "")
                                        Spacer(Modifier.width(2.dp))
                                        Text("取消")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    Spacer(Modifier.height(10.dp))
}
