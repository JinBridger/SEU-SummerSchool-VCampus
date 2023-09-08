package app.vcampus.client.scene.subscene.teachingaffairs

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.vcampus.client.scene.components.GradeListItem
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.shadowCustom
import app.vcampus.client.viewmodel.TeachingAffairsViewModel

@Composable
fun myGradeSubscene(viewModel: TeachingAffairsViewModel) {
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
            item{
                viewModel.myScheduleAndGrades.myClasses.forEach{
                    GradeListItem(it)
                    Spacer(Modifier.height(10.dp))
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
//                viewModel.StudentGradeItems.forEach {
//                    Surface(modifier = Modifier.fillMaxWidth().border(1.dp,
//                        color = Color.LightGray,
//                        shape = RoundedCornerShape(4.dp)
//                    ).animateContentSize(
//                        animationSpec = tween(
//                            durationMillis = 300,
//                            easing = LinearOutSlowInEasing
//                        )
//                    ).padding(10.dp)) {
//                        GradeListItem(it, viewModel)
//                    }
//                    Spacer(Modifier.height(10.dp))
//                }
        }
    }
}
//                Card(modifier = Modifier.fillMaxWidth().shadowCustom(
//                    blurRadius = 3.dp,
//                    shapeRadius = 3.dp)) {
//                    Column(modifier = Modifier.fillMaxWidth()) {
//                        viewModel.StudentGradeItems.forEach{
//                            GradeListItem(it,viewModel)
//                        }
//                    }
//                }
//                Spacer(modifier = Modifier.height(80.dp))
