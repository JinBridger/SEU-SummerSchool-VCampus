package app.vcampus.client.scene.subscene.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.bookList
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.LibraryViewModel

/**
 * return subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun returnSubscene(viewModel: LibraryViewModel) {
    var keyword by viewModel.returnBook.cardNumber
    val flag by viewModel.returnBook.searched

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().onPreviewKeyEvent { event: KeyEvent ->
            if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                viewModel.returnBook.getRecords()
                true
            } else {
                false
            }
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("办理还书", "办理还书业务")
                Spacer(Modifier.height(20.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = keyword,
                        onValueChange = { keyword = it },
                        label = { Text("搜索一卡通号") },
                        modifier = Modifier.padding(
                            0.dp, 0.dp, 16.dp,
                            0.dp
                        ).weight(1F)
                    )
                    Column {
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {
                                viewModel.returnBook.getRecords()
                            },
                            modifier = Modifier.height(56.dp)
                        ) {
                            Icon(Icons.Default.Search, "")
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            if (viewModel.returnBook.currentBorrowed.isNotEmpty()) {
                item {
                    Row {
                        Text(
                            keyword, fontWeight = FontWeight(700),
                            fontSize = 14.sp
                        )
                        Text(
                            " 当前借阅：${viewModel.returnBook.currentBorrowed.size} / 最大借阅：20",
                            fontSize = 14.sp
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                }

                viewModel.returnBook.currentBorrowed.forEach {
                    item(it.uuid) {
                        bookList(it, viewModel.returnBook::renewBook, viewModel.returnBook::returnBook)
                        Spacer(Modifier.height(10.dp))
                    }
                }
            } else if (flag) {
                item {
                    Text("未检索到此用户", fontSize = 14.sp)
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}