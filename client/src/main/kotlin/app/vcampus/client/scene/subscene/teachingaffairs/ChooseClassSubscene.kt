package app.vcampus.client.scene.subscene.teachingaffairs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.selectClassListItem
import app.vcampus.client.viewmodel.TeachingAffairsViewModel

@Composable
fun chooseClassSubscene(viewModel: TeachingAffairsViewModel) {
    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("选课", "选课系统")
                Spacer(Modifier.height(20.dp))
            }

            (0..3).forEach {
                item {
                    selectClassListItem(true)
                    Spacer(Modifier.height(10.dp))
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}