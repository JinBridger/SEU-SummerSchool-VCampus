package app.vcampus.client.scene.subscene.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.AdminViewModel

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
        }
    }
}