package app.vcampus.client.scene.subscene.teachingaffairs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.myClassListItem
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.TeachingAffairsViewModel

@Composable
fun myClassSubscene(viewModel: TeachingAffairsViewModel) {
    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("我的课堂", "查看教学班信息")
                Spacer(Modifier.height(20.dp))
            }

//            (0..3).forEach {
//                item {
//                    myClassListItem()
//                    Spacer(Modifier.height(10.dp))
//                }
//            }

            viewModel.myTeachingClasses.myClasses.forEach {
                item {
                    myClassListItem(it, viewModel.myTeachingClasses::saveStudentList)
                    Spacer(Modifier.height(10.dp))
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}