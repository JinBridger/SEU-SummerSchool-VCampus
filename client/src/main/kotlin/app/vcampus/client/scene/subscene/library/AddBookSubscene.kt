package app.vcampus.client.scene.subscene.library

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.LibraryViewModel

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addBookSubscene(viewModel: LibraryViewModel) {
    var isbn by viewModel.addBook.isbn
    val showDetails by viewModel.addBook.showDetails

    var name by viewModel.addBook.name
    var author by viewModel.addBook.author
    var press by viewModel.addBook.press
    var description by viewModel.addBook.description

//    val datePickerState = rememberDatePickerState()

    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("添加图书", "添加新的图书")
            }

            item {
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(value = isbn,
                            onValueChange = { isbn = it },
                            label = { Text("新增书籍 ISBN") },
                            modifier = Modifier.padding(0.dp, 0.dp, 16.dp,
                                    0.dp).weight(1F))
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
                            Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                Box(Modifier.fillMaxWidth()) {
                                    OutlinedTextField(value = name,
                                            onValueChange = { name = it },
                                            label = { Text("书名") },
                                            isError = name == "",
                                            trailingIcon = {
                                                if (name == "") Icon(
                                                        Icons.Filled.Error,
                                                        "error",
                                                        tint = MaterialTheme.colors.error)
                                            }, maxLines = 1,
                                            modifier = Modifier.fillMaxWidth())
                                }
                            }

                            Spacer(Modifier.height(16.dp))

                            Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                Box(Modifier.fillMaxWidth()) {
                                    OutlinedTextField(value = author,
                                            onValueChange = { author = it },
                                            label = { Text("作者") },
                                            isError = author == "",
                                            trailingIcon = {
                                                if (author == "") Icon(
                                                        Icons.Filled.Error,
                                                        "error",
                                                        tint = MaterialTheme.colors.error)
                                            }, maxLines = 1,
                                            modifier = Modifier.fillMaxWidth())
                                }
                            }

                            Spacer(Modifier.height(16.dp))

                            Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                Box(Modifier.fillMaxWidth()) {
                                    OutlinedTextField(value = press,
                                            onValueChange = { press = it },
                                            label = { Text("出版社") },
                                            isError = press == "",
                                            trailingIcon = {
                                                if (press == "") Icon(
                                                        Icons.Filled.Error,
                                                        "error",
                                                        tint = MaterialTheme.colors.error)
                                            }, maxLines = 1,
                                            modifier = Modifier.fillMaxWidth())
                                }
                            }

                            Spacer(Modifier.height(16.dp))

                            Row(Modifier.fillMaxWidth()) {
                                Box(Modifier.fillParentMaxWidth(1F)) {
                                    OutlinedTextField(value = description,
                                            onValueChange = { description = it },
                                            label = { Text("简介") },
                                            maxLines = 5,
                                            modifier = Modifier.fillMaxWidth())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}