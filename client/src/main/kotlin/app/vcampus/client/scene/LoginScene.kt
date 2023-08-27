package app.vcampus.client.scene

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.tempmodule.TempModule
import app.vcampus.client.viewmodel.LoginViewModel
import app.vcampus.client.viewmodel.MainPanelViewModel
import moe.tlaster.precompose.viewmodel.viewModel


@ExperimentalMaterialApi
@Composable
fun LoginScene(
    onLogin: () -> Unit,
) {
    val viewModel = viewModel(LoginViewModel::class, listOf()) {
        LoginViewModel()
    }

    var username by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(Modifier.shadow(elevation = 10.dp).size(1064.dp, 600.dp).background(Color.White)) {
            Row {
                Image(
                    painterResource("seu-side.png"),
                    contentDescription = "SEU Sidebar",
                    modifier = Modifier.width(600.dp).height(600.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .requiredWidth(328.dp)
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                        ) {
                            Column {
                                Text(
                                    text = "统一登录验证",
                                    style = TextStyle(
                                        fontSize = 34.sp,
                                        lineHeight = 36.sp,
                                        fontWeight = FontWeight(700),
                                        color = Color(0xDE000000),
                                        textAlign = TextAlign.Start,
                                    )
                                )

                                Text(
                                    text = "VCampus",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0x99000000),
                                        letterSpacing = 0.25.sp,
                                    )
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                            ) {
                                OutlinedTextField(
                                    value = username,
                                    onValueChange = { username = it },
                                    label = { Text("一卡通号") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text("密码") },
                                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    modifier = Modifier.fillMaxWidth(),
                                    trailingIcon = {
                                        val image = if (passwordVisible)
                                            Icons.Filled.Visibility
                                        else Icons.Filled.VisibilityOff

                                        // Please provide localized description for accessibility services
                                        val description = if (passwordVisible) "隐藏密码" else "显示密码"

                                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                            Icon(image, description)
                                        }
                                    }
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextButton(
                                    onClick = {
                                        println("忘记密码")
                                    }
                                ) {
                                    Text("忘记密码？")
                                }

                                Button(
                                    onClick = {
                                        if (viewModel.login(username, password)) {
                                            println("successful!")
                                            onLogin()
                                        } else {
                                            println("failed!")
                                        }
                                    }
                                ) {
                                    Text("登录")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}