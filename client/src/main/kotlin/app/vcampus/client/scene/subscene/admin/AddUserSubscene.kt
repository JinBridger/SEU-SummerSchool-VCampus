package app.vcampus.client.scene.subscene.admin

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.selectableChip
import app.vcampus.client.viewmodel.AdminViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun addUserSubscene(viewModel: AdminViewModel) {
    val selectedAdmin = remember { mutableStateOf(false) }
    val selectedStudent = remember { mutableStateOf(false) }
    val selectedTeacher = remember { mutableStateOf(false) }
    val selectedTeachingAffair = remember { mutableStateOf(false) }
    val selectedLibraryUser = remember { mutableStateOf(false) }
    val selectedLibraryStaff = remember { mutableStateOf(false) }
    val selectedShopUser = remember { mutableStateOf(false) }
    val selectedShopStaff = remember { mutableStateOf(false) }
    val selectedFinanceUser = remember { mutableStateOf(false) }
    val selectedFinanceStaff = remember { mutableStateOf(false) }

    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("添加账户", "添加登录账户")
                Spacer(Modifier.height(20.dp))
            }

            item {
                Text("一卡通号与初始密码", fontWeight = FontWeight(700),
                        fontSize = 20.sp)
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                            value = "",
                            onValueChange = { },
                            label = { Text("输入一卡通号") },
                            modifier = Modifier.padding(
                                    0.dp, 0.dp, 16.dp,
                                    0.dp
                            ).weight(1F)
                    )
                    OutlinedTextField(
                            value = "",
                            onValueChange = { },
                            label = { Text("输入初始密码") },
                            modifier = Modifier.padding(
                                    0.dp, 0.dp, 0.dp,
                                    0.dp
                            ).weight(1F)
                    )
                }
                Spacer(Modifier.height(20.dp))
                Text("用户权限", fontWeight = FontWeight(700), fontSize = 20.sp)
                Spacer(Modifier.height(10.dp))
                Surface(modifier = Modifier.fillMaxWidth().border(
                        1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(4.dp)
                ).padding(10.dp)) {
                    FlowRow(Modifier.fillMaxWidth()) {
                        selectableChip(selectedAdmin, "管理员")
                        Spacer(Modifier.width(10.dp))
                        selectableChip(selectedStudent, "学生")
                        Spacer(Modifier.width(10.dp))
                        selectableChip(selectedTeacher, "教师")
                        Spacer(Modifier.width(10.dp))
                        selectableChip(selectedTeachingAffair, "教务")
                        Spacer(Modifier.width(10.dp))
                        selectableChip(selectedLibraryUser, "图书馆使用权限")
                        Spacer(Modifier.width(10.dp))
                        selectableChip(selectedLibraryStaff, "图书馆管理权限")
                        Spacer(Modifier.width(10.dp))
                        selectableChip(selectedShopUser, "商店使用权限")
                        Spacer(Modifier.width(10.dp))
                        selectableChip(selectedShopStaff, "商店管理权限")
                        Spacer(Modifier.width(10.dp))
                        selectableChip(selectedFinanceUser, "财务使用权限")
                        Spacer(Modifier.width(10.dp))
                        selectableChip(selectedFinanceStaff, "财务管理权限")
                    }
                }
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.weight(1F))
                    Button(onClick = {}) {
                        Text("添加账户")
                    }
                }
            }
        }
    }
}