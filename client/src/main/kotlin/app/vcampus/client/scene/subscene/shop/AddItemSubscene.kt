package app.vcampus.client.scene.subscene.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.addShopItem
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.ShopViewModel

/**
 * add item subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun addItemSubscene(viewModel: ShopViewModel) {

    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 100.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Spacer(Modifier.height(80.dp))
                    pageTitle("添加商品", "添加新的商品")
                    addShopItem(viewModel)
                }
            }
        }
    }
}