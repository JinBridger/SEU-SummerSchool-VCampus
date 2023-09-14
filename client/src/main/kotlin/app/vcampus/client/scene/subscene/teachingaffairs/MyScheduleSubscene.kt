package app.vcampus.client.scene.subscene.teachingaffairs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.classTable
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.scene.components.sarasaUiSc
import app.vcampus.client.viewmodel.TeachingAffairsViewModel

/**
 * my schedule subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun myScheduleSubscene(viewModel: TeachingAffairsViewModel) {
    var currentWeek by remember { mutableStateOf(1) }

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
                pageTitle("我的课表", "查看个人课表")
                Spacer(Modifier.height(20.dp))
            }

            item {
                Spacer(Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.weight(1F))
                    IconButton(onClick = {
                        currentWeek -= 1
                    }, enabled = currentWeek > 1) {
                        Icon(Icons.Default.KeyboardArrowLeft, null)
                    }

                    Spacer(Modifier.width(20.dp))

                    Text("第 $currentWeek 周", fontSize = 20.sp, fontFamily = sarasaUiSc)

                    Spacer(Modifier.width(20.dp))

                    IconButton(onClick = {
                        currentWeek += 1
                    }, enabled = currentWeek < 16) {
                        Icon(Icons.Default.KeyboardArrowRight, null)
                    }

                    Spacer(Modifier.weight(1F))
                }
                Spacer(Modifier.height(20.dp))
            }

            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    classTable(viewModel, currentWeek)
                    Spacer(Modifier.height(80.dp))
                }
            }
        }
    }
}