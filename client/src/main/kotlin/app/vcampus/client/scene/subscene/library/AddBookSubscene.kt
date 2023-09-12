package app.vcampus.client.scene.subscene.library

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.Select
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.LibraryViewModel
import app.vcampus.server.enums.BookStatus
import kotlinx.coroutines.launch

/**
 * add book subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun addBookSubscene(viewModel: LibraryViewModel) {
    val showDetails by viewModel.addBook.showDetails

    var isbn by viewModel.addBook.newBook.value.isbn
    var name by viewModel.addBook.newBook.value.name
    var author by viewModel.addBook.newBook.value.author
    var press by viewModel.addBook.newBook.value.press
    var description by viewModel.addBook.newBook.value.description
    var place by viewModel.addBook.newBook.value.place
    var cover by viewModel.addBook.newBook.value.cover
    var callNumber by viewModel.addBook.newBook.value.callNumber
    var bookStatus by viewModel.addBook.newBook.value.bookStatus

    val scope = rememberCoroutineScope()

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Spacer(Modifier.height(80.dp))
                    pageTitle("添加图书", "添加新的图书")
                }

                item {
                    Spacer(Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .onPreviewKeyEvent { event: KeyEvent ->
                                if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                    viewModel.addBook.preAddBook()
                                    true
                                } else {
                                    false
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = isbn,
                            onValueChange = { isbn = it },
                            label = { Text("新增书籍 ISBN") },
                            modifier = Modifier.padding(
                                0.dp, 0.dp, 16.dp,
                                0.dp
                            ).weight(1F)
                        )
                        Column {
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = {
                                viewModel.addBook.preAddBook()
                            }, modifier = Modifier.height(56.dp)) {
                                Text("继续")
                            }
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(16.dp))

                    Crossfade(showDetails) { show ->
                        if (show) {
                            Column {
                                Divider()
                                Spacer(Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(Modifier.fillParentMaxWidth(0.32F)) {
                                        OutlinedTextField(
                                            value = name,
                                            onValueChange = { name = it },
                                            label = { Text("书名") },
                                            isError = name == "",
                                            trailingIcon = {
                                                if (name == "") Icon(
                                                    Icons.Filled.Error,
                                                    "error",
                                                    tint = MaterialTheme.colors.error
                                                )
                                            },
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    Box(Modifier.fillParentMaxWidth(0.32F)) {
                                        OutlinedTextField(
                                            value = author,
                                            onValueChange = { author = it },
                                            label = { Text("作者") },
                                            isError = author == "",
                                            trailingIcon = {
                                                if (author == "") Icon(
                                                    Icons.Filled.Error,
                                                    "error",
                                                    tint = MaterialTheme.colors.error
                                                )
                                            },
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    Box(Modifier.fillParentMaxWidth(0.32F)) {
                                        OutlinedTextField(
                                            value = press,
                                            onValueChange = { press = it },
                                            label = { Text("出版社") },
                                            isError = press == "",
                                            trailingIcon = {
                                                if (press == "") Icon(
                                                    Icons.Filled.Error,
                                                    "error",
                                                    tint = MaterialTheme.colors.error
                                                )
                                            },
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }

                                Spacer(Modifier.height(16.dp))

                                Row(Modifier.fillMaxWidth()) {
                                    Box(Modifier.fillMaxWidth()) {
                                        OutlinedTextField(
                                            value = description,
                                            onValueChange = { description = it },
                                            label = { Text("简介") },
                                            maxLines = 5,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }

                                Spacer(Modifier.height(16.dp))

                                Row(Modifier.fillMaxWidth()) {
                                    Box(Modifier.fillMaxWidth()) {
                                        OutlinedTextField(
                                            value = cover,
                                            onValueChange = { cover = it },
                                            label = { Text("封面图片链接") },
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }

                                Spacer(Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(Modifier.fillParentMaxWidth(0.32F)) {
                                        OutlinedTextField(
                                            value = place,
                                            onValueChange = { place = it },
                                            label = { Text("书籍位置") },
                                            isError = place == "",
                                            trailingIcon = {
                                                if (place == "") Icon(
                                                    Icons.Filled.Error,
                                                    "error",
                                                    tint = MaterialTheme.colors.error
                                                )
                                            },
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    Box(Modifier.fillParentMaxWidth(0.32F)) {
                                        OutlinedTextField(
                                            value = callNumber,
                                            onValueChange = { callNumber = it },
                                            label = { Text("索书号") },
                                            isError = callNumber == "",
                                            trailingIcon = {
                                                if (callNumber == "") Icon(
                                                    Icons.Filled.Error,
                                                    "error",
                                                    tint = MaterialTheme.colors.error
                                                )
                                            },
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    Box(Modifier.fillParentMaxWidth(0.32F)) {
                                        Select(
                                            selectList = BookStatus.entries,
                                            label = { Text("书籍状态") },
                                            setValue = { bookStatus = it }
                                        )
                                    }
                                }

                                Spacer(Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = {
                                            viewModel.addBook.addBook()
                                        }
                                    ) {
                                        Text("添加")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Box(Modifier.align(Alignment.BottomCenter)) {
                Spacer(Modifier.height(16.dp))

                val state = remember {
                    SnackbarHostState()
                }
                SnackbarHost(hostState = state)

                Crossfade(viewModel.addBook.showMessage) {
                    if (it.value) {
                        if (viewModel.addBook.result.value) {
                            scope.launch {
                                state.showSnackbar(
                                    "已成功添加书籍", "关闭"
                                )
                            }
                        } else {
                            scope.launch {
                                state.showSnackbar(
                                    "添加书籍失败", "关闭"
                                )
                            }
                        }

                        viewModel.addBook.showMessage.value = false
                    }
                }
            }
        }
    }
}