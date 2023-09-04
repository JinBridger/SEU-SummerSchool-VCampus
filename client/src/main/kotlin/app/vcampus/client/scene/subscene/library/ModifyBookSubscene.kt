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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.searchBookListItem
import app.vcampus.client.viewmodel.LibraryViewModel

@Composable
fun modifyBookSubscene(viewModel: LibraryViewModel) {
    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("修改图书", "修改现有图书")
            }
            item {
                Spacer(Modifier.height(20.dp))
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                            value = "",
                            onValueChange = {  },
                            label = { Text("搜索图书（支持模糊搜索）") },
                            modifier = Modifier.padding(
                                    0.dp, 0.dp, 16.dp,
                                    0.dp
                            ).weight(1F)
                    )
                    Column {
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
//                            viewModel.addBook.preAddBook()
                        }, modifier = Modifier.height(56.dp)) {
                            Icon(Icons.Default.Search, "")
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                Text("共检索到10条结果", fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
            }

//            (0..10).forEach {
//                item {
//                    searchBookListItem(listOf(), true)
//                }
//            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}