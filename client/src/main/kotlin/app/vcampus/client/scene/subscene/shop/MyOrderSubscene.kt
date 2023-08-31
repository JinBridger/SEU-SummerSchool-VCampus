package app.vcampus.client.scene.subscene.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.ShopViewModel

@Composable
fun myOrderSubscene(viewModel: ShopViewModel) {
    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 100.dp)) {
        Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Spacer(Modifier.height(80.dp))
                    pageTitle("我的订单", "查看所有订单")
                }
            }
        }
    }
}