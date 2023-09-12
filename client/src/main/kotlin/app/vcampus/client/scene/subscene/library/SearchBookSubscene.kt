package app.vcampus.client.scene.subscene.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.searchBookListItem
import app.vcampus.client.viewmodel.LibraryViewModel

/**
 * search book subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun searchBookSubscene(viewModel: LibraryViewModel) {
    var keyword by viewModel.searchBook.keyword
    val searched by viewModel.searchBook.searched

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("查询图书", "查找图书馆藏书")
            }

            item {
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .onPreviewKeyEvent { event: KeyEvent ->
                            if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                viewModel.searchBook.searchBook()
                                true
                            } else {
                                false
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = keyword,
                        onValueChange = { keyword = it },
                        label = { Text("搜索图书（支持模糊搜索）") },
                        modifier = Modifier.padding(
                            0.dp, 0.dp, 16.dp,
                            0.dp
                        ).weight(1F)
                    )
                    Column {
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
                            viewModel.searchBook.searchBook()
                        }, modifier = Modifier.height(56.dp)) {
                            Icon(Icons.Default.Search, "")
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(20.dp))

                if (viewModel.searchBook.bookList.isNotEmpty()) {
                    Text("共检索到 ${viewModel.searchBook.bookList.size} 条结果", fontSize = 14.sp)

                    Spacer(Modifier.height(8.dp))
                } else {
                    if (keyword.isBlank() || !searched) {
                        Text("输入任意内容以搜索...", fontSize = 14.sp)
                    } else {
                        Text("未检索到任何结果", fontSize = 14.sp)
                    }
                }
            }

            viewModel.searchBook.bookList.forEach {
                item(it.key) {
                    searchBookListItem(it.value)
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}