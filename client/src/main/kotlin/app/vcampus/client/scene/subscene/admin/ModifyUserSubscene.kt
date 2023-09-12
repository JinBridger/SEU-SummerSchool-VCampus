package app.vcampus.client.scene.subscene.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.userListItem
import app.vcampus.client.viewmodel.AdminViewModel

/**
 * modify user subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun modifyUserSubscene(viewModel: AdminViewModel) {
    var keyword by viewModel.modifyUser.keyword

    LaunchedEffect(Unit) {
        viewModel.modifyUser.init()
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("修改账户", "修改账户权限 / 密码")
                Spacer(Modifier.height(20.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .onPreviewKeyEvent { event: KeyEvent ->
                            if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                viewModel.modifyUser.searchUser()
                                true
                            } else {
                                false
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = keyword,
                        onValueChange = { keyword = it },
                        label = { Text("搜索用户") },
                        modifier = Modifier.padding(
                            0.dp, 0.dp, 16.dp,
                            0.dp
                        ).weight(1F)
                    )
                    Column {
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
                            viewModel.modifyUser.searchUser()
                        }, modifier = Modifier.height(56.dp)) {
                            Icon(Icons.Default.Search, "")
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            viewModel.modifyUser.userList.forEach {
                item(it.cardNum) {
                    userListItem(it, viewModel.modifyUser::modifyUser)
                    Spacer(Modifier.height(10.dp))
                }

            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}