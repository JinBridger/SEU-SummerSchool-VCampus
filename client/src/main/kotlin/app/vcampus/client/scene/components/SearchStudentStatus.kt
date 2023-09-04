package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material.DataTable
import app.vcampus.client.viewmodel.StudentStatusViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchStudentStatusItem(
                            isEditable: Boolean = true,
                            viewModel: StudentStatusViewModel) {

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


    var expanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    Surface(modifier = Modifier.fillMaxWidth().border(
        1.dp,
        color = Color.LightGray,
        shape = RoundedCornerShape(4.dp)
    ).animateContentSize(
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    ), onClick = { expanded = !expanded }) {
        Box(Modifier.fillMaxSize().padding(10.dp)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxHeight()) {
                        Row {
                            Text(
                                school,
                                fontWeight = FontWeight(700),
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                major,
                                fontWeight = FontWeight(700),
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Text(
                                "学号:${cardNumber} ",
                                fontWeight = FontWeight(700),
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "一卡通号:${studentNumber} ",
                                fontWeight = FontWeight(700),
                                color = Color.DarkGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    Row {
                        Text(
                            familyName,
                            fontWeight = FontWeight(700),
                            fontSize = 20.sp
                        )
                        Text(
                            givenName,
                            fontWeight = FontWeight(700),
                            fontSize = 20.sp
                        )
                    }
                }
                if (expanded) {
                    if (!isEditing) {
                        Spacer(Modifier.height(6.dp))
                        Divider()
                        Spacer(Modifier.height(6.dp))
                        Column {
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = familyName,
                                    onValueChange = { familyName = it },
                                    label = { Text("姓") },
                                    isError = familyName == "",
                                    readOnly = true,
                                    trailingIcon = {
                                        if (familyName == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                                Spacer(Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = givenName,
                                    onValueChange = { givenName = it },
                                    label = { Text("名") },
                                    isError = givenName == "",
                                    readOnly = true,
                                    trailingIcon = {
                                        if (givenName == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                                Spacer(Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = gender,
                                    onValueChange = { gender = it },
                                    label = { Text("性别") },
                                    isError = gender == "",
                                    readOnly = true,
                                    trailingIcon = {
                                        if (gender == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                                Spacer(Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = birthDate,
                                    onValueChange = { birthDate = it },
                                    label = { Text("出生日期") },
                                    isError = birthDate == "", readOnly = true,
                                    trailingIcon = {
                                        if (birthDate == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                            }
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = birthPlace,
                                    onValueChange = { birthPlace = it },
                                    label = { Text("籍贯") },
                                    isError = birthPlace == "", readOnly = true,
                                    trailingIcon = {
                                        if (birthPlace == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                                Spacer(Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = politicalStatus,
                                    onValueChange = { politicalStatus = it },
                                    label = { Text("政治面貌") },
                                    isError = politicalStatus == "", readOnly = true,
                                    trailingIcon = {
                                        if (politicalStatus == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                                Spacer(Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = status,
                                    onValueChange = { status = it },
                                    label = { Text("学籍状态") },
                                    isError = status == "", readOnly = true,
                                    trailingIcon = {
                                        if (status == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.5F),
                                    value = major,
                                    onValueChange = { major = it },
                                    label = { Text("专业") },
                                    isError = major == "", readOnly = true,
                                    trailingIcon = {
                                        if (major == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.5F),
                                    value = school,
                                    onValueChange = { school = it },
                                    label = { Text("学院") },
                                    isError = school == "", readOnly = true,
                                    trailingIcon = {
                                        if (school == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                            }
                        }
                        if (isEditable) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Spacer(Modifier.weight(1F))
                                Button(onClick = { isEditing = true }) {
                                    Text("修改学生学籍信息")
                                }
                            }
                        }
                    } else {
                        Spacer(Modifier.height(6.dp))
                        Divider()
                        Spacer(Modifier.height(6.dp))
                        Column {
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = familyName,
                                    onValueChange = { familyName = it },
                                    label = { Text("姓") },
                                    isError = familyName == "",
                                    readOnly = false,
                                    trailingIcon = {
                                        if (familyName == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                                Spacer(Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = givenName,
                                    onValueChange = { givenName = it },
                                    label = { Text("名") },
                                    isError = givenName == "",
                                    readOnly = false,
                                    trailingIcon = {
                                        if (givenName == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                                Spacer(Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = gender,
                                    onValueChange = { gender = it },
                                    label = { Text("性别") },
                                    isError = gender == "",
                                    readOnly = false,
                                    trailingIcon = {
                                        if (gender == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    }
                                )
                                Spacer(Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = birthDate,
                                    onValueChange = { birthDate = it },
                                    label = { Text("出生日期") },
                                    isError = birthDate == "", readOnly = false,
                                    trailingIcon = {
                                        if (birthDate == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                            }
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = birthPlace,
                                    onValueChange = { birthPlace = it },
                                    label = { Text("籍贯") },
                                    isError = birthPlace == "", readOnly = false,
                                    trailingIcon = {
                                        if (birthPlace == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                                Spacer(Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = politicalStatus,
                                    onValueChange = { politicalStatus = it },
                                    label = { Text("政治面貌") },
                                    isError = politicalStatus == "", readOnly = false,
                                    trailingIcon = {
                                        if (politicalStatus == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                                Spacer(Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.25F),
                                    value = status,
                                    onValueChange = { status = it },
                                    label = { Text("学籍状态") },
                                    isError = status == "", readOnly = false,
                                    trailingIcon = {
                                        if (status == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.5F),
                                    value = major,
                                    onValueChange = { major = it },
                                    label = { Text("专业") },
                                    isError = major == "", readOnly = false,
                                    trailingIcon = {
                                        if (major == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.weight(0.5F),
                                    value = school,
                                    onValueChange = { school = it },
                                    label = { Text("学院") },
                                    isError = school == "", readOnly = false,
                                    trailingIcon = {
                                        if (school == "") Icon(
                                            Icons.Filled.Error, "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    })
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Spacer(Modifier.weight(1F))
                            Button(onClick = { isEditing = false }) {
                                Text("保存修改")
                            }
                        }
                    }
                }
            }
        }
    }
}