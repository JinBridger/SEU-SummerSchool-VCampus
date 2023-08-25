package app.vcampus.client

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import io.netty.channel.Channel

@Composable
@Preview
fun loginWindow() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    MaterialTheme {
        Box(Modifier.size(1064.dp, 600.dp).background(Color.White)) {
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
//                                style = MaterialTheme.typography.h4
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
//                                style = MaterialTheme.typography.subtitle1
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
                                    visualTransformation = PasswordVisualTransformation(),
                                    modifier = Modifier.fillMaxWidth()
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
                                        println("登录")
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

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        resizable = false,
        state = WindowState(size = DpSize.Unspecified),
        title = "登录 - VCampus"
    ) {
            loginWindow()
    }
}
