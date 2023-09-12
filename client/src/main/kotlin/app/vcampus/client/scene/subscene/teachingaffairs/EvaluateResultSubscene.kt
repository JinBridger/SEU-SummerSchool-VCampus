package app.vcampus.client.scene.subscene.teachingaffairs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.evaluateResultListItem
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.TeachingAffairsViewModel

/**
 * evaluate result subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun evaluateResultSubscene(viewModel: TeachingAffairsViewModel) {
    LaunchedEffect(Unit) {
        viewModel.myTeachingClasses.init()
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("评教结果", "查看评教结果")
                Spacer(Modifier.height(20.dp))
            }

            viewModel.myTeachingClasses.myClasses.forEach {
                item(it.uuid) {
                    evaluateResultListItem(it)
                    Spacer(Modifier.height(10.dp))
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}