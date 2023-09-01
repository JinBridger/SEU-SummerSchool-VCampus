package app.vcampus.client.scene.subscene.teachingaffairs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.classTable
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.TeachingAffairsViewModel

@Composable
fun myScheduleSubscene(viewModel: TeachingAffairsViewModel) {
    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("我的课表", "查看个人课表")
                Spacer(Modifier.height(20.dp))
            }

            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    classTable(viewModel)
                    Spacer(Modifier.height(80.dp))
                }
            }
        }
    }
}