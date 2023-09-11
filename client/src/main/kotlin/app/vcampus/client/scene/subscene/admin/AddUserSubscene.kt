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
import app.vcampus.client.scene.components.Select
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.roleChip
import app.vcampus.client.scene.components.selectableChip
import app.vcampus.client.viewmodel.AdminViewModel
import app.vcampus.server.enums.Gender

@Composable
fun addUserSubscene(viewModel: AdminViewModel) {


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
                            modifier = Modifier.weight(1F)
                    )
                    Spacer(Modifier.width(16.dp))
                    OutlinedTextField(
                            value = "",
                            onValueChange = { },
                            label = { Text("输入初始密码") },
                            modifier = Modifier.weight(1F)
                    )
                }

                Spacer(Modifier.height(20.dp))
                Text("基本信息", fontWeight = FontWeight(700),
                    fontSize = 20.sp)
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("姓名") },
                        modifier = Modifier.weight(1F)
                    )
                    Spacer(Modifier.width(16.dp))
                    Select(
                        selectList = Gender.entries,
                        label = {
                            Text("性别")
                        },
                        value = Gender.unspecified,
                        setValue = { },
                        modifier = Modifier.weight(1F)
                    )
                    Spacer(Modifier.width(16.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("电话") },
                        modifier = Modifier.weight(1F)
                    )
                    Spacer(Modifier.width(16.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text("邮件") },
                        modifier = Modifier.weight(1F)
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
                    roleChip {
                        println(it)
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