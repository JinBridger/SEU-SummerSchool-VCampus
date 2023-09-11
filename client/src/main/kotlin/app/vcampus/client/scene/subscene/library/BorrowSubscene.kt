package app.vcampus.client.scene.subscene.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.LibraryViewModel

@Composable
fun borrowSubscene(viewModel: LibraryViewModel) {
    var bookUuid by remember { mutableStateOf("") }
    var cardId by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("办理借书", "办理借书业务")
                Spacer(Modifier.height(20.dp))
            }

            item {
                OutlinedTextField(
                    value = bookUuid,
                    onValueChange = { bookUuid = it },
                    label = { Text("输入书籍标识号") },
                    modifier = Modifier.padding(
                        0.dp, 0.dp, 0.dp,
                        0.dp
                    ).fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = cardId,
                        onValueChange = { cardId = it },
                        label = { Text("输入借书人一卡通号") },
                        modifier = Modifier.padding(
                            0.dp, 0.dp, 16.dp,
                            0.dp
                        ).weight(1F)
                    )
                    Column {
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {
                                viewModel.borrowBook(bookUuid, cardId)
                            },
                            modifier = Modifier.height(56.dp)
                        ) {
                            Text("借阅")
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}