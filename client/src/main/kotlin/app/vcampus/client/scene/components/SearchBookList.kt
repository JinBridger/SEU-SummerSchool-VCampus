package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.viewmodel.MutableLibraryBook
import app.vcampus.server.entity.LibraryBook
import app.vcampus.server.enums.BookStatus
import app.vcampus.server.utility.Pair
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material.DataTable
import java.util.stream.Collectors

/**
 * search book list item component, used in `SearchBookSubscene`
 *
 * @param _bookList the list of books
 * @param isEditable whether it could be edited
 * @param onEdit function when edit the list
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalStdlibApi::class)
@Composable
fun searchBookListItem(
    _bookList: List<LibraryBook>,
    isEditable: Boolean = false,
    onEdit: (LibraryBook, Boolean) -> Unit = { _: LibraryBook, _: Boolean -> }
) {
    var expanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var bookList = _bookList.toList()
    val primaryBook = bookList[0]

    var name by remember { mutableStateOf(primaryBook.name) }
    var author by remember { mutableStateOf(primaryBook.author) }
    var press by remember { mutableStateOf(primaryBook.press) }
    var cover by remember { mutableStateOf(primaryBook.cover) }
    var description by remember { mutableStateOf(primaryBook.description) }

    var modifiedBooks = mutableListOf<Pair<MutableLibraryBook, MutableState<Boolean>>>()

    bookList.forEach {
        val newBook = MutableLibraryBook()
        newBook.fromLibraryBook(it)
        modifiedBooks.add(Pair(newBook, mutableStateOf(false)))
    }

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
            Column {
                Row {
                    Column {
                        Text(name, fontWeight = FontWeight(700))
                        Text(author)
                        Spacer(Modifier.height(4.dp))
                        Text(press, fontSize = 14.sp)
                    }
                    Spacer(Modifier.weight(1F))
                    Column {
                        Text(
                            "馆藏副本：" + modifiedBooks.size, fontSize = 12.sp,
                            fontWeight = FontWeight(700),
                            color = Color.Gray
                        )
                        Text(
                            "可借副本：" + modifiedBooks.stream()
                                .filter { book -> book.first.bookStatus.value == BookStatus.available }
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
                                    Text(description)
                                }
                                Spacer(Modifier.height(4.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Box(
                                    modifier = Modifier.fillMaxWidth().aspectRatio(
                                        0.7F
                                    ).clip(
                                        RoundedCornerShape(4.dp)
                                    ).background(
                                        Color.Cyan
                                    )
                                ) {
                                    AsyncImage(
                                        load = { loadImageBitmap(cover) },
                                        painterFor = { remember { BitmapPainter(it) } },
                                        contentDescription = "",
                                        contentScale = ContentScale.FillBounds
                                    )
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
                                        2F
                                    )
                                ) {
                                    Text(
                                        "索书号",
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
                                        "标识码",
                                        fontWeight = FontWeight(
                                            700
                                        )
                                    )
                                },
                                DataColumn(
                                    width = TableColumnWidth.Flex(
                                        6F
                                    )
                                ) {
                                    Text(
                                        "馆藏地",
                                        fontWeight = FontWeight(
                                            700
                                        )
                                    )
                                },
                                DataColumn(
                                    width = TableColumnWidth.Flex(
                                        1F
                                    )
                                ) {
                                    Text(
                                        "书籍状态",
                                        fontWeight = FontWeight(
                                            700
                                        )
                                    )
                                },
                            )
                        ) {
                            modifiedBooks.forEach {
                                row {
                                    cell {
                                        Text(it.first.callNumber.value)
                                    }
                                    cell {
                                        Text(it.first.uuid.toString().split("-").last())
                                    }
                                    cell {
                                        Text(it.first.place.value)
                                    }
                                    cell {
                                        Text(
                                            it.first.bookStatus.value.label,
                                            color = Color(it.first.bookStatus.value.color)
                                        )
                                    }
                                }
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
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("书名") },
                                modifier = Modifier.padding(
                                    0.dp, 0.dp, 16.dp,
                                    0.dp
                                ).weight(1F),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = author,
                                onValueChange = { author = it },
                                label = { Text("作者") },
                                modifier = Modifier.padding(
                                    0.dp, 0.dp, 16.dp,
                                    0.dp
                                ).weight(1F),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = press,
                                onValueChange = { press = it },
                                label = { Text("出版社") },
                                modifier = Modifier.padding(
                                    0.dp, 0.dp, 0.dp,
                                    0.dp
                                ).weight(1F),
                                singleLine = true
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = cover,
                                onValueChange = { cover = it },
                                label = { Text("封面图片链接") },
                                modifier = Modifier.padding(
                                    0.dp, 0.dp, 16.dp,
                                    0.dp
                                ).weight(2F),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = primaryBook.isbn,
                                onValueChange = { },
                                enabled = false,
                                label = { Text("ISBN") },
                                modifier = Modifier.padding(
                                    0.dp, 0.dp, 0.dp,
                                    0.dp
                                ).weight(1F),
                                singleLine = true
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = { Text("简介") },
                                modifier = Modifier.padding(
                                    0.dp, 0.dp, 0.dp,
                                    0.dp
                                ).weight(1F),
                                maxLines = 5
                            )
                        }

                        DataTable(
                            modifier = Modifier.fillMaxWidth(),
                            columns = listOf(
                                DataColumn(
                                    width = TableColumnWidth.Flex(
                                        2F
                                    )
                                ) {
                                    Text(
                                        "索书号",
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
                                        "标识码",
                                        fontWeight = FontWeight(
                                            700
                                        )
                                    )
                                },
                                DataColumn(
                                    width = TableColumnWidth.Flex(
                                        6F
                                    )
                                ) {
                                    Text(
                                        "馆藏地",
                                        fontWeight = FontWeight(
                                            700
                                        )
                                    )
                                },
                                DataColumn(
                                    width = TableColumnWidth.Flex(
                                        4F
                                    )
                                ) {
                                    Text(
                                        "书籍状态",
                                        fontWeight = FontWeight(
                                            700
                                        )
                                    )
                                },
                                DataColumn(
                                    width = TableColumnWidth.Flex(
                                        1F
                                    )
                                ) {
                                    Text(
                                        "操作",
                                        fontWeight = FontWeight(
                                            700
                                        )
                                    )
                                }
                            )
                        ) {
                            modifiedBooks.forEach {
                                row {
                                    cell {
                                        Box(
                                            modifier = Modifier.fillMaxSize(0.9F)
                                                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
                                                .padding(4.dp)
                                        ) {
                                            BasicTextField(
                                                value = it.first.callNumber.value,
                                                onValueChange = { v -> it.first.callNumber.value = v },
                                                textStyle = TextStyle(textDecoration = if (it.second.value) TextDecoration.LineThrough else TextDecoration.None)
                                            )
                                        }
                                    }
                                    cell {
                                        Text(
                                            it.first.uuid.toString().split("-").last(),
                                            textDecoration = if (it.second.value) TextDecoration.LineThrough else TextDecoration.None
                                        )
                                    }
                                    cell {
                                        Box(
                                            modifier = Modifier.fillMaxSize(0.9F)
                                                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
                                                .padding(4.dp)
                                        ) {
                                            BasicTextField(
                                                value = it.first.place.value,
                                                onValueChange = { v -> it.first.place.value = v },
                                                textStyle = TextStyle(textDecoration = if (it.second.value) TextDecoration.LineThrough else TextDecoration.None)
                                            )
                                        }
                                    }
                                    cell {
                                        Box(
                                            modifier = Modifier.fillMaxSize(0.9F)
                                                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
                                                .padding(4.dp)
                                        ) {
                                            Select(
                                                selectList = BookStatus.entries,
                                                label = { Text("书籍状态") },
                                                setValue = { v -> it.first.bookStatus.value = v },
                                                value = it.first.bookStatus.value,
                                                basic = true,
                                                textStyle = TextStyle(textDecoration = if (it.second.value) TextDecoration.LineThrough else TextDecoration.None)
                                            )
                                        }
                                    }
                                    cell {
                                        TextButton(onClick = {
                                            it.second.value = !it.second.value
                                        }) {
                                            if (it.second.value) {
                                                Text("撤销删除")
                                            } else {
                                                Text("删除副本")
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (isEditing) {
                            Spacer(Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(Modifier.weight(1F))
                                Button(onClick = {

                                    val i = modifiedBooks.iterator()

                                    while (i.hasNext()) {
                                        val pair = i.next()

                                        pair.first.name.value = name
                                        pair.first.author.value = author
                                        pair.first.press.value = press
                                        pair.first.cover.value = cover
                                        pair.first.description.value = description

                                        onEdit(pair.first.toLibraryBook(), pair.second.value)

                                        if (pair.second.value) {
                                            i.remove()
                                        }
                                    }

                                    bookList = modifiedBooks.map { it.first.toLibraryBook() }

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
                                    name = primaryBook.name
                                    author = primaryBook.author
                                    press = primaryBook.press
                                    cover = primaryBook.cover
                                    description = primaryBook.description

                                    modifiedBooks = mutableListOf()

                                    bookList.forEach {
                                        val newBook = MutableLibraryBook()
                                        newBook.fromLibraryBook(it)
                                        modifiedBooks.add(Pair(newBook, mutableStateOf(false)))
                                    }

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
