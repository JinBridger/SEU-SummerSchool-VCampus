package app.vcampus.client.scene.subscene.teachingaffairs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.GradeListItem
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.TeachingAffairsViewModel

/**
 * my grade subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun myGradeSubscene(viewModel: TeachingAffairsViewModel) {
    LaunchedEffect(Unit) {
        viewModel.myClasses.init()
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("我的成绩", "查看个人成绩单")
                Spacer(Modifier.height(20.dp))
            }
            item {
                viewModel.myClasses.selected.forEach {
                    if (it.selectRecord.grade != null) {
                        GradeListItem(it)
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}