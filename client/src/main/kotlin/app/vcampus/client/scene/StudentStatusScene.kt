package app.vcampus.client.scene

import DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.NavRail
import app.vcampus.client.scene.components.TopBar
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.StudentStatusViewModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StudentStatusForStudent(viewModel: StudentStatusViewModel) {
    var familyName by viewModel.familyName
    var givenName by viewModel.givenName
    var gender by viewModel.gender
    var birthDate by viewModel.birthDate
    var major by viewModel.major
    var school by viewModel.school
    var cardNumber by viewModel.cardNumber
    var studentNumber by viewModel.studentNumber

    var showDatePicker by remember { mutableStateOf(false) }

    val sdf = SimpleDateFormat("yyyy年MM月dd日")

    if (showDatePicker) {
        DatePicker(
            initDate = Date(),
            onDismissRequest = { showDatePicker = false },
            onDateSelect = {
                birthDate = it
                showDatePicker = false
            },
            minYear = 1900
        )
    }

    LazyColumn {
        item {
            Spacer(Modifier.height(50.dp))
            pageTitle("个人学籍信息", "查看修改个人学籍信息")
        }

        item {
            Spacer(Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth()) {
                Box(Modifier.fillParentMaxWidth(0.25F).padding(5.dp)) {
                    OutlinedTextField(
                        value = familyName,
                        onValueChange = { familyName = it },
                        label = { Text("姓") },
                        isError = familyName == "",
                        trailingIcon = {
                            if (familyName == "")
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colors.error)
                        },
                    )
                }

                Box(Modifier.fillParentMaxWidth(0.25F).padding(5.dp)) {
                    OutlinedTextField(
                        value = givenName,
                        onValueChange = { givenName = it },
                        label = { Text("名") },
                        isError = givenName == "",
                        trailingIcon = {
                            if (givenName == "")
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colors.error)
                        },
                    )
                }

                Box(Modifier.fillParentMaxWidth(0.20F).padding(5.dp)) {
                    OutlinedTextField(
                        value = gender,
                        onValueChange = { gender = it },
                        label = { Text("性别") },
                        isError = gender == "",
                        trailingIcon = {
                            if (gender == "")
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colors.error)
                        },
                    )
                }

                Box(Modifier.fillParentMaxWidth(0.30F).padding(5.dp)) {
                    OutlinedTextField(
                        value = sdf.format(birthDate),
                        onValueChange = {},
                        label = { Text("出生日期") },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showDatePicker = true
                            }) {
                                Icon(Icons.Default.ExpandMore, "")
                            }
                        }
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
                        trailingIcon = {
                            if (major == "")
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colors.error)
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
                        trailingIcon = {
                            if (school == "")
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colors.error)
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
                        trailingIcon = {
                            if (cardNumber == "")
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colors.error)
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
                        trailingIcon = {
                            if (studentNumber == "")
                                Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colors.error)
                        },
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = {}) {
                    Row {
                        Icon(Icons.Default.Done, "")
                        Spacer(Modifier.width(5.dp))
                        Text("保存修改")
                    }
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun StudentStatusScene(
    navi: Navigator
) {
    val viewModel = viewModel(StudentStatusViewModel::class, listOf()) {
        StudentStatusViewModel()
    }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar("学籍管理")
        }
    ) {
        Row {
            NavRail(navi, "/student_status")
            Box(Modifier.fillMaxSize()) {
                Box(Modifier.width(800.dp).align(Alignment.TopCenter)) {
                    StudentStatusForStudent(viewModel)
                }
            }
        }
    }
}