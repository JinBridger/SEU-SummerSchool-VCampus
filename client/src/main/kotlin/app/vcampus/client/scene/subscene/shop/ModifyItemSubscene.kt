package app.vcampus.client.scene.subscene.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.EditStoreItem
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.ShopViewModel

/**
 * modify item subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun modifyItemSubscene(viewModel: ShopViewModel) {
    var keyword by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 100.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Column {
                        Spacer(Modifier.height(80.dp))
                        pageTitle("修改商品", "修改商品信息")
                    }
                }
                item {
                    Spacer(Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .onPreviewKeyEvent { event: KeyEvent ->
                                if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                    viewModel.modifyStoreItem.searchStoreItem(keyword)
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
                            label = { Text("搜索商品（支持模糊搜索）") },
                            modifier = Modifier.padding(
                                0.dp, 0.dp, 16.dp,
                                0.dp
                            ).weight(1F)
                        )
                        Column {
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = {
                                viewModel.modifyStoreItem.searchStoreItem(keyword)
                            }, modifier = Modifier.height(56.dp)) {
                                Icon(Icons.Default.Search, "")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                }
                viewModel.modifyStoreItem.storeList.forEach {
                    item(it.uuid) {
                        EditStoreItem(it, isEditable = true, onEdit = { storeItem ->
                            viewModel.modifyStoreItem.updateStoreItem(storeItem)
                        })
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}