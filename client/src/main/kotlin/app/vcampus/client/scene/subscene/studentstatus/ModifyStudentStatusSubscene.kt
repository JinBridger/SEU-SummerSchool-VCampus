package app.vcampus.client.scene.subscene.studentstatus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.SearchStudentStatusItem
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.StudentStatusViewModel

/**
 * modify student status subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun modifyStudentStatusSubscene(viewModel: StudentStatusViewModel) {
    var keyword by viewModel.searchKeyword

    LaunchedEffect(Unit) {
        viewModel.searchStudent()
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("修改学籍信息", "修改个人学籍信息")
            }

            item {
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .onPreviewKeyEvent { event: KeyEvent ->
                            if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                viewModel.searchStudent()
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
                        label = { Text("搜索学生（模糊搜索）") },
                        modifier = Modifier.padding(
                            0.dp, 0.dp, 16.dp,
                            0.dp
                        ).weight(1F)
                    )
                    Column {
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
                            viewModel.searchStudent()
                        }, modifier = Modifier.height(56.dp)) {
                            Icon(Icons.Default.Search, "")
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                Text("搜索到的学生学籍信息", fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
            }

            viewModel.searchedStudents.forEach { student ->
                item(student.cardNumber) {
                    SearchStudentStatusItem(student, true) {
                        viewModel.updateStudent(it)
                    }

                    Spacer(Modifier.height(8.dp))
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}