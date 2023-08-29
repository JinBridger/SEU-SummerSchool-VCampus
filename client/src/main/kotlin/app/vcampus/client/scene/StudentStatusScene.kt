package app.vcampus.client.scene

import DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.*
import app.vcampus.client.viewmodel.StudentStatusViewModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import java.text.SimpleDateFormat
import java.util.*


val studentStatusSideBarItem = listOf(
    SideBarItem(true, "学籍信息", "", Icons.Default.Info),
    SideBarItem(false, "我的学籍信息", "查看我的学籍信息", Icons.Default.Info)
)


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
    var birthPlace by viewModel.birthPlace
    var politicalStatus by viewModel.politicalStatus
    var status by viewModel.status

    var showFullScreenDialog by remember { mutableStateOf(false) }

    if (showFullScreenDialog) {
        FullScreenDialog(onDismissRequest = { showFullScreenDialog = false },
            onConfirmed = { showFullScreenDialog = false })
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        SideBar(studentStatusSideBarItem)
        Box(
            modifier = Modifier.fillMaxHeight().fillMaxWidth().shadowCustom(offsetX = 3.dp, blurRadius = 10.dp).background(Color.White)
                .padding(horizontal = 100.dp)
        ) {
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
                                        if (familyName == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
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
                                        if (givenName == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
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
                                        if (gender == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
                                    },
                                )
                            }

                            Box(Modifier.fillParentMaxWidth(0.30F).padding(5.dp)) {
                                OutlinedTextField(value = birthDate,
                                    onValueChange = { birthDate = it },
                                    label = { Text("出生日期") },
                                    isError = birthDate == "",
                                    readOnly = true,
                                    trailingIcon = {
                                        if (birthDate == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
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
                                        if (birthPlace == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
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
                                        if (politicalStatus == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
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
                                        if (status == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
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
                                        if (major == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
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
                                        if (school == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
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
                                        if (cardNumber == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
                                            tint = MaterialTheme.colors.error
                                        )
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
                                        if (studentNumber == "") Icon(
                                            Icons.Filled.Error,
                                            "error",
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

//    Scaffold(scaffoldState = scaffoldState, topBar = {
//        TopBar("学籍管理")
//    }) {
//    Row {
//        NavRail(navi, "/student_status")
//            Box(Modifier.fillMaxSize()) {
//                Box(Modifier.width(800.dp).align(Alignment.TopCenter)) {
        StudentStatusForStudent(viewModel)
//                }
//            }
//    }
//    }
}