package app.vcampus.client.scene.subscene.studentstatus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.StudentStatusViewModel

/**
 * student status subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun studentStatusSubscene(viewModel: StudentStatusViewModel) {
    var familyName by viewModel.student.value.familyName
    var givenName by viewModel.student.value.givenName
    var gender by viewModel.student.value.gender
    var birthDate by viewModel.student.value.birthDate
    var major by viewModel.student.value.major
    var school by viewModel.student.value.school
    var cardNumber by viewModel.student.value.cardNumber
    var studentNumber by viewModel.student.value.studentNumber
    var birthPlace by viewModel.student.value.birthPlace
    var politicalStatus by viewModel.student.value.politicalStatus
    var status by viewModel.student.value.status

    LaunchedEffect(Unit) {
        viewModel.getStudentStatus()
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("个人学籍信息", "查看个人学籍信息")
            }

            item {
                Spacer(Modifier.height(50.dp))
                Row(Modifier.fillMaxWidth()) {
                    Box(Modifier.fillParentMaxWidth(0.25F).padding(5.dp)) {
                        OutlinedTextField(
                            value = familyName,
                            onValueChange = { },
                            label = { Text("姓") },
                            readOnly = true,
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.25F).padding(5.dp)) {
                        OutlinedTextField(
                            value = givenName,
                            onValueChange = { },
                            label = { Text("名") },
                            readOnly = true,
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.20F).padding(5.dp)) {
                        OutlinedTextField(
                            value = gender.label,
                            onValueChange = { },
                            label = { Text("性别") },
                            readOnly = true,
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.30F).padding(5.dp)) {
                        OutlinedTextField(
                            value = birthDate,
                            onValueChange = { birthDate = it },
                            label = { Text("出生日期") },
                            readOnly = true,
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Box(Modifier.fillParentMaxWidth(0.4F).padding(5.dp)) {
                        OutlinedTextField(
                            value = birthPlace,
                            onValueChange = { birthPlace = it },
                            label = { Text("籍贯") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.3F).padding(5.dp)) {
                        OutlinedTextField(
                            value = politicalStatus.label,
                            onValueChange = { },
                            label = { Text("政治面貌") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.3F).padding(5.dp)) {
                        OutlinedTextField(
                            value = status.label,
                            onValueChange = { },
                            label = { Text("学籍状态") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Box(Modifier.fillParentMaxWidth(0.5F).padding(5.dp)) {
                        OutlinedTextField(
                            value = major,
                            onValueChange = { major = it },
                            label = { Text("专业") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.5F).padding(5.dp)) {
                        OutlinedTextField(
                            value = school,
                            onValueChange = { school = it },
                            label = { Text("学院") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Box(Modifier.fillParentMaxWidth(0.5F).padding(5.dp)) {
                        OutlinedTextField(
                            value = cardNumber.toString(),
                            onValueChange = { },
                            label = { Text("一卡通号") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.5F).padding(5.dp)) {
                        OutlinedTextField(
                            value = studentNumber,
                            onValueChange = { studentNumber = it },
                            label = { Text("学号") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = studentNumber == "",
                            readOnly = true,
                            trailingIcon = {
                                if (studentNumber == "") Icon(
                                    Icons.Filled.Error, "error",
                                    tint = MaterialTheme.colors.error
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}