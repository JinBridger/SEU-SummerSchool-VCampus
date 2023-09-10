package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun userListItem() {
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

    val expanded = remember { mutableStateOf(false) }
    Surface(modifier = Modifier.fillMaxWidth().border(
            1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
    ).animateContentSize(
            animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
            )
    ).padding(10.dp)) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("王小明", fontWeight = FontWeight(700))
                    Text("212212212", fontWeight = FontWeight(700))
                }
                Spacer(Modifier.weight(1F))
                Button(onClick = { expanded.value = !expanded.value }) {
                    Text("编辑")
                }
            }

            if (expanded.value) {
                Spacer(Modifier.height(8.dp))
                Divider(Modifier.fillMaxWidth())
                Spacer(Modifier.height(12.dp))
                Text("新的密码", fontWeight = FontWeight(700))
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                            value = "",
                            onValueChange = { },
                            label = { Text("输入新的密码") },
                            modifier = Modifier.padding(
                                    0.dp, 0.dp, 0.dp,
                                    0.dp
                            ).weight(1F)
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text("用户权限", fontWeight = FontWeight(700))
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
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.weight(1F))
                    Button(onClick = {}) {
                        Text("修改账户")
                    }
                }
            }
        }
    }
}