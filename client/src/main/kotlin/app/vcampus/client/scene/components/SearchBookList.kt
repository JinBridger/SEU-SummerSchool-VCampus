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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.server.entity.LibraryBook
import app.vcampus.server.enums.BookStatus
import app.vcampus.server.utility.Pair
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material.DataTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import java.util.stream.Collectors

@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                // instead of printing to console, you can also write this to log,
                // or show some error placeholder
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)

@OptIn(ExperimentalMaterialApi::class, ExperimentalStdlibApi::class)
@Composable
fun searchBookListItem(bookList: List<LibraryBook>, isEditable: Boolean = false) {
    var expanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    val primaryBook = bookList[0]

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
                        Text(primaryBook.name, fontWeight = FontWeight(700))
                        Text(primaryBook.author)
                        Spacer(Modifier.height(4.dp))
                        Text(primaryBook.press, fontSize = 14.sp)
                    }
                    Spacer(Modifier.weight(1F))
                    Column {
                        Text("馆藏副本：" + bookList.size, fontSize = 12.sp,
                                fontWeight = FontWeight(700),
                                color = Color.Gray)
                        Text("可借副本：" + bookList.stream().filter { book -> book.bookStatus.equals(BookStatus.available) }
                            .collect(Collectors.counting()), fontSize = 12.sp,
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
                                    Text(primaryBook.isbn)
                                }
                                Spacer(Modifier.height(4.dp))
                                Row {
                                    Text("简介: ", fontWeight = FontWeight(700))
                                    Text(primaryBook.description)
                                }
                                Spacer(Modifier.height(4.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Box(modifier = Modifier.fillMaxWidth().aspectRatio(
                                        0.7F).clip(
                                        RoundedCornerShape(4.dp)).background(
                                        Color.Cyan)) {
                                    AsyncImage(
                                        load = { loadImageBitmap(primaryBook.cover) },
                                        painterFor = { remember { BitmapPainter(it) } },
                                        contentDescription = "",
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
                            bookList.forEach {
                                row {
                                    cell {
                                        Text(it.callNumber)
                                    }
                                    cell {
                                        Text(it.uuid.toString().split("-").last())
                                    }
                                    cell {
                                        Text(it.place)
                                    }
                                    cell {
                                        Text(it.bookStatus.label)
                                    }
                                }
                            }
//                            row {
//                                cell { Text("TP312C/98/.4") }
//                                cell { Text("2847857") }
//                                cell { Text("工业技术图书阅览室（九龙湖A401）") }
//                                cell { Text("可借", color = Color(0xff508e54)) }
//                            }
//                            row {
//                                cell { Text("TP312C/98/.4") }
//                                cell { Text("2847859") }
//                                cell { Text("工业技术图书阅览室（九龙湖A401）") }
//                                cell { Text("可借", color = Color(0xff508e54)) }
//                            }
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
