package app.vcampus.client.scene.subscene.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.returnBookListItem
import app.vcampus.client.viewmodel.LibraryViewModel

@Composable
fun borrowSubscene(viewModel: LibraryViewModel) {
    val isExpanded = remember { mutableStateOf(false) }

    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
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
                            value = "",
                            onValueChange = { },
                            label = { Text("搜索一卡通号") },
                            modifier = Modifier.padding(
                                    0.dp, 0.dp, 16.dp,
                                    0.dp
                            ).weight(1F)
                    )
                    Column {
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { isExpanded.value = true },
                                modifier = Modifier.height(56.dp)) {
                            Icon(Icons.Default.Search, "")
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            if (isExpanded.value) {
                item {
                    Row {
                        Text("212212212", fontWeight = FontWeight(700),
                                fontSize = 14.sp)
                        Text(" 当前借阅：10 / 最大借阅：20",
                                fontSize = 14.sp)
                    }
                    Spacer(Modifier.height(8.dp))
                }

                (0..10).forEach {
                    item {
                        returnBookListItem()
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}