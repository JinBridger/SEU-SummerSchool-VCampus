package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.server.entity.User

/**
 * user list item component, used in `ModifyUserSubscene`
 *
 * @param user the user
 * @param onEdit function when edit
 */
@Composable
fun userListItem(user: User, onEdit: (Int, String, List<String>) -> Unit = { _, _, _ -> }) {
    val expanded = remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var roles by remember { mutableStateOf(user.roles.toList()) }

    Surface(
        modifier = Modifier.fillMaxWidth().border(
            1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
        ).animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        ).padding(10.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(user.name, fontWeight = FontWeight(700))
                    Text(user.cardNum.toString(), fontWeight = FontWeight(700))
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
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("输入新的密码（留空则不修改）") },
                        modifier = Modifier.weight(1F)
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text("用户权限", fontWeight = FontWeight(700))
                roleChip(user.roles.toList()) {
                    roles = it
                }
                Spacer(Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.weight(1F))
                    Button(onClick = {
                        onEdit(user.cardNum, password, roles)
                    }) {
                        Text("修改账户")
                    }
                }
            }
        }
    }
}