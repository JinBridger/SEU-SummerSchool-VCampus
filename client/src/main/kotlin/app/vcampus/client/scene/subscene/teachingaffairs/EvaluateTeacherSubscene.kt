package app.vcampus.client.scene.subscene.teachingaffairs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.evaluateTeacherListItem
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.TeachingAffairsViewModel

/**
 * evaluate teacher subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun evaluateTeacherSubscene(viewModel: TeachingAffairsViewModel) {
    LaunchedEffect(Unit) {
        viewModel.myClasses.init()
    }

    val isAny = mutableStateOf(false)

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("评教", "评价教师教学")
                Spacer(Modifier.height(20.dp))
            }

            viewModel.myClasses.selected.forEach {
                if (it.selectRecord.grade != null && !it.isEvaluated) {
                    isAny.value = true
                    item(it.uuid) {
                        evaluateTeacherListItem(viewModel, it)
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }

            if (!isAny.value) {
                item {
                    Text("所有已完成课程均已评教。")
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}