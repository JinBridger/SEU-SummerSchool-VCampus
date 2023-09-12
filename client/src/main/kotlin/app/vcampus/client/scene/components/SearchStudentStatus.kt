package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.viewmodel.MutableStudent
import app.vcampus.server.entity.IEntity
import app.vcampus.server.entity.Student
import app.vcampus.server.enums.Gender
import app.vcampus.server.enums.PoliticalStatus
import app.vcampus.server.enums.StudentStatus

/**
 * search student status item component, used in `ModifyStudentStatusSubscene`
 *
 * @param _student the student
 * @param isEditable whether it could be edited
 * @param onEdit function when edit
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchStudentStatusItem(
    _student: Student,
    isEditable: Boolean = true,
    onEdit: (Student) -> Unit,
) {
    var student = IEntity.fromJson(_student.toJson(), Student::class.java)
    val mutableStudent = MutableStudent()
    mutableStudent.fromStudent(student)

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
                                mutableStudent.school.value,
                                fontWeight = FontWeight(700),
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                mutableStudent.major.value,
                                fontWeight = FontWeight(700),
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Text(
                                "学号：${mutableStudent.studentNumber.value} ",
                                fontWeight = FontWeight(700),
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "一卡通号：${mutableStudent.cardNumber.value.toString()} ",
                                fontWeight = FontWeight(700),
                                color = Color.DarkGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    Row {
                        Text(
                            mutableStudent.familyName.value,
                            fontWeight = FontWeight(700),
                            fontSize = 20.sp
                        )
                        Text(
                            mutableStudent.givenName.value,
                            fontWeight = FontWeight(700),
                            fontSize = 20.sp
                        )
                    }
                }
                if (expanded) {
                    Spacer(Modifier.height(6.dp))
                    Divider()
                    Spacer(Modifier.height(6.dp))
                    Column {
                        Row {
                            OutlinedTextField(
                                modifier = Modifier.weight(0.25F),
                                value = mutableStudent.familyName.value,
                                onValueChange = { mutableStudent.familyName.value = it },
                                label = { Text("姓") },
                                isError = mutableStudent.familyName.value == "",
                                readOnly = !isEditing,
                                trailingIcon = {
                                    if (mutableStudent.familyName.value == "") Icon(
                                        Icons.Filled.Error, "error",
                                        tint = MaterialTheme.colors.error
                                    )
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            OutlinedTextField(
                                modifier = Modifier.weight(0.25F),
                                value = mutableStudent.givenName.value,
                                onValueChange = { mutableStudent.givenName.value = it },
                                label = { Text("名") },
                                isError = mutableStudent.givenName.value == "",
                                readOnly = !isEditing,
                                trailingIcon = {
                                    if (mutableStudent.givenName.value == "") Icon(
                                        Icons.Filled.Error, "error",
                                        tint = MaterialTheme.colors.error
                                    )
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            Box(modifier = Modifier.weight(0.25F)) {
                                Select(
                                    selectList = Gender.entries,
                                    label = { Text("性别") },
                                    value = mutableStudent.gender.value,
                                    setValue = {
                                        mutableStudent.gender.value = it
                                    },
                                    readOnly = !isEditing
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                            OutlinedTextField(
                                modifier = Modifier.weight(0.25F),
                                value = mutableStudent.birthDate.value,
                                onValueChange = { mutableStudent.birthDate.value = it },
                                label = { Text("出生日期") },
                                isError = mutableStudent.birthDate.value == "",
                                readOnly = !isEditing,
                                trailingIcon = {
                                    if (mutableStudent.birthDate.value == "") Icon(
                                        Icons.Filled.Error, "error",
                                        tint = MaterialTheme.colors.error
                                    )
                                })
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            OutlinedTextField(
                                modifier = Modifier.weight(0.25F),
                                value = mutableStudent.birthPlace.value,
                                onValueChange = { mutableStudent.birthPlace.value = it },
                                label = { Text("籍贯") },
                                isError = mutableStudent.birthPlace.value == "",
                                readOnly = !isEditing,
                                trailingIcon = {
                                    if (mutableStudent.birthPlace.value == "") Icon(
                                        Icons.Filled.Error, "error",
                                        tint = MaterialTheme.colors.error
                                    )
                                })
                            Spacer(Modifier.width(8.dp))
//                                OutlinedTextField(
//                                    modifier = Modifier.weight(0.25F),
//                                    value = politicalStatus,
//                                    onValueChange = { politicalStatus = it },
//                                    label = { Text("政治面貌") },
//                                    isError = politicalStatus == "",
//                                    readOnly = !isEditing,
//                                    trailingIcon = {
//                                        if (politicalStatus == "") Icon(
//                                            Icons.Filled.Error, "error",
//                                            tint = MaterialTheme.colors.error
//                                        )
//                                    })
                            Box(modifier = Modifier.weight(0.25F)) {
                                Select(
                                    selectList = PoliticalStatus.entries,
                                    label = { Text("政治面貌") },
                                    value = mutableStudent.politicalStatus.value,
                                    setValue = {
                                        mutableStudent.politicalStatus.value = it
                                    },
                                    readOnly = !isEditing
                                )
                            }
                            Spacer(Modifier.width(8.dp))
//                                OutlinedTextField(
//                                    modifier = Modifier.weight(0.25F),
//                                    value = status,
//                                    onValueChange = { status = it },
//                                    label = { Text("学籍状态") },
//                                    isError = status == "",
//                                    readOnly = !isEditing,
//                                    trailingIcon = {
//                                        if (status == "") Icon(
//                                            Icons.Filled.Error, "error",
//                                            tint = MaterialTheme.colors.error
//                                        )
//                                    })
                            Box(modifier = Modifier.weight(0.25F)) {
                                Select(
                                    selectList = StudentStatus.entries,
                                    label = { Text("学籍状态") },
                                    value = mutableStudent.status.value,
                                    setValue = {
                                        mutableStudent.status.value = it
                                    },
                                    readOnly = !isEditing
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            OutlinedTextField(
                                modifier = Modifier.weight(0.32F),
                                value = mutableStudent.studentNumber.value,
                                onValueChange = { mutableStudent.studentNumber.value = it },
                                label = { Text("学号") },
                                isError = mutableStudent.studentNumber.value == "",
                                readOnly = !isEditing,
                                trailingIcon = {
                                    if (mutableStudent.studentNumber.value == "") Icon(
                                        Icons.Filled.Error, "error",
                                        tint = MaterialTheme.colors.error
                                    )
                                })
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(
                                modifier = Modifier.weight(0.32F),
                                value = mutableStudent.major.value,
                                onValueChange = { mutableStudent.major.value = it },
                                label = { Text("专业") },
                                isError = mutableStudent.major.value == "",
                                readOnly = !isEditing,
                                trailingIcon = {
                                    if (mutableStudent.major.value == "") Icon(
                                        Icons.Filled.Error, "error",
                                        tint = MaterialTheme.colors.error
                                    )
                                })
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(
                                modifier = Modifier.weight(0.32F),
                                value = mutableStudent.school.value,
                                onValueChange = { mutableStudent.school.value = it },
                                label = { Text("学院") },
                                isError = mutableStudent.school.value == "",
                                readOnly = !isEditing,
                                trailingIcon = {
                                    if (mutableStudent.school.value == "") Icon(
                                        Icons.Filled.Error, "error",
                                        tint = MaterialTheme.colors.error
                                    )
                                })
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        if (isEditable) {
                            if (!isEditing) {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Spacer(Modifier.weight(1F))
                                    Button(onClick = { isEditing = true }) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Edit, "")
                                            Spacer(Modifier.width(2.dp))
                                            Text("修改学生学籍信息")
                                        }
                                    }
                                }
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(Modifier.weight(1F))
                                    Button(onClick = {
                                        student = mutableStudent.toStudent()

                                        onEdit(student)

                                        isEditing = false
                                    }) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Done, "")
                                            Spacer(Modifier.width(2.dp))
                                            Text("确认")
                                        }
                                    }
                                    Spacer(Modifier.width(8.dp))
                                    Button(onClick = {
                                        mutableStudent.fromStudent(student)

                                        isEditing = false
                                    }) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Close, "")
                                            Spacer(Modifier.width(2.dp))
                                            Text("取消")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}