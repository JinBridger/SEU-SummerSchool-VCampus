package app.vcampus.client.scene

import androidx.compose.animation.Crossfade
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.shadowCustom
import app.vcampus.client.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModel

/**
 * login scene
 *
 * @param onLogin function when login
 */
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
    val loginState by viewModel.loginState
    val scope = rememberCoroutineScope()
    var showServerAddress by remember { mutableStateOf(false) }
    var address by viewModel.server
    val openForgetPasswordDialog = remember { mutableStateOf(false) }

    when (loginState) {
        true -> onLogin()
        false -> {}
    }

    if (openForgetPasswordDialog.value) {
        AlertDialog(
                onDismissRequest = {
                    openForgetPasswordDialog.value = false
                },
                title = {
                    Text(text = "重置密码", fontWeight = FontWeight(700))
                },
                text = {
                    Text(
                            "如需重置密码可发送邮件至webmaster@seu.edu.cn，或拨打网络与信息中心技术服务热线：025-83790808-893。"
                    )
                },
                buttons = {
                    Row(
                            modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(Modifier.weight(1F))
                        TextButton(
                                onClick = { openForgetPasswordDialog.value = false }
                        ) {
                            Text("确定")
                        }
                    }
                }
        )
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            Modifier.size(1064.dp, 600.dp).shadowCustom(
                blurRadius = 10.dp
            ).background(Color.White)
        ) {
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
                            verticalArrangement = Arrangement.spacedBy(
                                16.dp, Alignment.CenterVertically
                            )
                        ) {
                            Column {
                                pageTitle("统一认证登录", "VCampus")
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    8.dp, Alignment.CenterVertically
                                ),
                                modifier = Modifier.onPreviewKeyEvent { event: KeyEvent ->
                                    if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                        viewModel.login(username, password)
                                        true
                                    } else {
                                        false
                                    }
                                }
                            ) {
                                OutlinedTextField(
                                    value = username,
                                    onValueChange = { username = it },
                                    label = { Text("一卡通号") },
                                    modifier = Modifier.fillMaxWidth().onPreviewKeyEvent { event: KeyEvent ->
                                        if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                            viewModel.login(
                                                username,
                                                password
                                            )
                                            true
                                        } else {
                                            false
                                        }
                                    },
                                    singleLine = true
                                )

                                OutlinedTextField(
                                    value = password,
                                    onValueChange = {
                                        password = it
                                    },
                                    label = { Text("密码") },
                                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    modifier = Modifier.fillMaxWidth(),
                                    trailingIcon = {
                                        val image = if (passwordVisible)
                                            Icons.Filled.Visibility
                                        else Icons.Filled.VisibilityOff

                                        // Please provide localized description for accessibility services
                                        val description = if (passwordVisible) "隐藏密码" else "显示密码"

                                        IconButton(
                                            onClick = { passwordVisible = !passwordVisible }) {
                                            Icon(image, description)
                                        }
                                    },
                                    singleLine = true
                                )

                                if (showServerAddress) {
                                    OutlinedTextField(
                                        value = address,
                                        onValueChange = { address = it },
                                        label = { Text("服务器地址") },
                                        modifier = Modifier.fillMaxWidth().onPreviewKeyEvent { event: KeyEvent ->
                                            if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                                viewModel.login(
                                                    username,
                                                    password
                                                )
                                                true
                                            } else {
                                                false
                                            }
                                        },
                                        singleLine = true
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                TextButton(
                                    onClick = {
                                        openForgetPasswordDialog.value = true
                                    }
                                ) {
                                    Text("忘记密码？")
                                }

                                TextButton(
                                    onClick = {
                                        showServerAddress = !showServerAddress
                                    }
                                ) {
                                    Text("设置服务器地址")
                                }

                                Spacer(Modifier.weight(1F))

                                Button(
                                    onClick = {
                                        viewModel.login(username, password)
                                    }
                                ) {
                                    Text("登录")
                                }
                            }
                        }

                        Box(Modifier.align(Alignment.BottomCenter)) {
                            Spacer(Modifier.height(16.dp))

                            val state = remember {
                                SnackbarHostState()
                            }
                            SnackbarHost(hostState = state)

                            Crossfade(viewModel.showMessage) {
                                if (it.value) {
                                    scope.launch {
                                        if (!FakeRepository.isConnected) {
                                            state.showSnackbar(
                                                "无法连接到服务器", "关闭"
                                            )
                                        } else {
                                            state.showSnackbar(
                                                "一卡通号或密码不正确", "关闭"
                                            )
                                        }
                                    }

                                    password = ""
                                    viewModel.showMessage.value = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}