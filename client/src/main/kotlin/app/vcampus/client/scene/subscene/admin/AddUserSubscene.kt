package app.vcampus.client.scene.subscene.admin

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.Select
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.roleChip
import app.vcampus.client.viewmodel.AdminViewModel
import app.vcampus.server.entity.User
import app.vcampus.server.enums.Gender

/**
 * add user subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun addUserSubscene(viewModel: AdminViewModel) {
    var cardNum by viewModel.addUser.cardNum
    var name by viewModel.addUser.name
    var password by viewModel.addUser.password
    var gender by viewModel.addUser.gender
    var phone by viewModel.addUser.phone
    var email by viewModel.addUser.email

    var roles by viewModel.addUser.roles

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("添加账户", "添加登录账户")
                Spacer(Modifier.height(20.dp))
            }

            item {
                Text(
                    "一卡通号与初始密码", fontWeight = FontWeight(700),
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = cardNum,
                        onValueChange = {
                            if (it.chars().allMatch(Character::isDigit)) cardNum = it
                        },
                        label = { Text("输入一卡通号") },
                        modifier = Modifier.weight(1F)
                    )
                    Spacer(Modifier.width(16.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("输入初始密码") },
                        modifier = Modifier.weight(1F)
                    )
                }

                Spacer(Modifier.height(20.dp))
                Text(
                    "基本信息", fontWeight = FontWeight(700),
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
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
                        setValue = {
                            gender = it
                        },
                        modifier = Modifier.weight(1F)
                    )
                    Spacer(Modifier.width(16.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            if (it.chars().allMatch(Character::isDigit)) phone = it
                        },
                        label = { Text("电话") },
                        modifier = Modifier.weight(1F)
                    )
                    Spacer(Modifier.width(16.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("邮件") },
                        modifier = Modifier.weight(1F)
                    )
                }

                Spacer(Modifier.height(20.dp))
                Text("用户权限", fontWeight = FontWeight(700), fontSize = 20.sp)
                Spacer(Modifier.height(10.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth().border(
                        1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(4.dp)
                    ).padding(10.dp)
                ) {
                    roleChip {
                        roles = it
                    }
                }
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.weight(1F))
                    Button(onClick = {
                        val user = User()

                        user.cardNum = cardNum.toInt()
                        user.name = name
                        user.password = password
                        user.gender = gender
                        user.phone = phone
                        user.email = email
                        user.roles = roles.toTypedArray()

                        viewModel.addUser.addUser(user)
                    }) {
                        Text("添加账户")
                    }
                }
            }
        }
    }
}