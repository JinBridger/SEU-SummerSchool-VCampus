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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.StudentStatusViewModel

@Composable
fun studentStatusSubscene(viewModel: StudentStatusViewModel) {
    var familyName by viewModel.familyName
    var givenName by viewModel.givenName
    var gender by viewModel.gender
    var birthDate by viewModel.birthDate
    var major by viewModel.major
    var school by viewModel.school
    var cardNumber by viewModel.cardNumber
    var studentNumber by viewModel.studentNumber
    var birthPlace by viewModel.birthPlace
    var politicalStatus by viewModel.politicalStatus
    var status by viewModel.status


    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {

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
                                onValueChange = { familyName = it },
                                label = { Text("姓") },
                                isError = familyName == "",
                                readOnly = true,
                                trailingIcon = {
                                    if (familyName == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                },
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.25F).padding(5.dp)) {
                        OutlinedTextField(
                                value = givenName,
                                onValueChange = { givenName = it },
                                label = { Text("名") },
                                isError = givenName == "",
                                readOnly = true,
                                trailingIcon = {
                                    if (givenName == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                },
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.20F).padding(5.dp)) {
                        OutlinedTextField(
                                value = gender,
                                onValueChange = { gender = it },
                                label = { Text("性别") },
                                isError = gender == "",
                                readOnly = true,
                                trailingIcon = {
                                    if (gender == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                },
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.30F).padding(5.dp)) {
                        OutlinedTextField(value = birthDate, onValueChange = { birthDate = it }, label = { Text("出生日期") }, isError = birthDate == "", readOnly = true, trailingIcon = {
                            if (birthDate == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                        })
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
                                isError = birthPlace == "",
                                readOnly = true,
                                trailingIcon = {
                                    if (birthPlace == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                },
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.3F).padding(5.dp)) {
                        OutlinedTextField(
                                value = politicalStatus,
                                onValueChange = { politicalStatus = it },
                                label = { Text("政治面貌") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = politicalStatus == "",
                                readOnly = true,
                                trailingIcon = {
                                    if (politicalStatus == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                },
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.3F).padding(5.dp)) {
                        OutlinedTextField(
                                value = status,
                                onValueChange = { status = it },
                                label = { Text("学籍状态") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = status == "",
                                readOnly = true,
                                trailingIcon = {
                                    if (status == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                },
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
                                isError = major == "",
                                readOnly = true,
                                trailingIcon = {
                                    if (major == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                },
                        )
                    }

                    Box(Modifier.fillParentMaxWidth(0.5F).padding(5.dp)) {
                        OutlinedTextField(
                                value = school,
                                onValueChange = { school = it },
                                label = { Text("学院") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = school == "",
                                readOnly = true,
                                trailingIcon = {
                                    if (school == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                },
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Box(Modifier.fillParentMaxWidth(0.5F).padding(5.dp)) {
                        OutlinedTextField(
                                value = cardNumber,
                                onValueChange = { cardNumber = it },
                                label = { Text("一卡通号") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = cardNumber == "",
                                readOnly = true,
                                trailingIcon = {
                                    if (cardNumber == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                },
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
                                    if (studentNumber == "") Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                },
                        )
                    }
                }
            }
        }
    }
}