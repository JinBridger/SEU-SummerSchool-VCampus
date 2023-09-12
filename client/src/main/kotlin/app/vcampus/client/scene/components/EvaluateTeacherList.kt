package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.client.viewmodel.TeachingAffairsViewModel
import app.vcampus.server.entity.TeachingClass
import app.vcampus.server.utility.Pair

/**
 * the evaluation items
 */
val evaluateItem = listOf(
    "注重理论与应用的结合，激发学生的学习兴趣和主动性",
    "讲课条理清楚，重点突出，详略得当",
    "能根据教学内容恰当运用多种教学方法和手段",
    "批改作业认真，课下指导、交流细致"
)

/**
 * rating bar component, shows the checkboxes
 *
 * @param evaluateString the evaluate question
 * @param ptList the list of rates
 * @param inx the index position of bar
 */
@Composable
fun ratingBar(
    evaluateString: String, ptList: List<MutableState<Boolean>>,
    inx: Int
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            evaluateString,
            fontWeight = FontWeight(700)
        )
        Row(Modifier.fillMaxWidth()) {
            (0..9).forEach { idx ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Checkbox(
                        checked = ptList[inx * 10 + idx].value,
                        onCheckedChange = {
                            ((inx * 10)..(inx * 10 + 9)).forEach {
                                ptList[it].value = false
                            }
                            ptList[inx * 10 + idx].value = true
                        }
                    )
                    Text((idx + 1).toString())
                }
            }
        }
        Spacer(Modifier.height(10.dp))
    }
}

/**
 * evaluation teacher list item component, used in `EvaluateTeacherSubscene`
 *
 * @param viewModel the viewmodel of subscene
 * @param teachingClass the teaching class of the item
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun evaluateTeacherListItem(viewModel: TeachingAffairsViewModel, teachingClass: TeachingClass) {
    var expanded by remember { mutableStateOf(false) }
    val pointList = remember { List(40) { mutableStateOf(false) } }
    var comment by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxWidth().border(
        1.dp,
        color = Color.LightGray,
        shape = RoundedCornerShape(4.dp)
    ).animateContentSize(
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    ), onClick = { expanded = !expanded }) {
        Box(Modifier.fillMaxSize().padding(10.dp)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxHeight()) {
                        Row {
                            Text(
                                text = teachingClass.teacherName,
                                fontWeight = FontWeight(700),
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Row {
                            Text(
                                text = teachingClass.course.courseName,
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = teachingClass.course.courseId,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    Text(
                        "待评教",
                        fontWeight = FontWeight(700),
                    )
                }

                if (expanded) {
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                    (0..<4).forEach {
                        ratingBar(evaluateItem[it], pointList, it)
                    }
                    Text("其他想要评价的内容：", fontWeight = FontWeight(700))
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        comment, onValueChange = { comment = it }, maxLines = 5,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(Modifier.weight(1F))
                        Button(onClick = {
                            // pack the result
                            // <teachingClassUuid, <[Q1.point, Q2.point, ....], "Comment">>
                            val resultList = List(4) { -1 }.toMutableList()

                            (0..3).forEach { inx ->
                                (0..9).forEach { idx ->
                                    if (pointList[inx * 10 + idx].value) {
                                        resultList[inx] = idx + 1
                                    }
                                }
                            }
                            val result = Pair(teachingClass.uuid, Pair(resultList.toList(), comment))

                            resultList.forEach {
                                if (it == -1) {
                                    return@Button
                                }
                            }

                            viewModel.myClasses.sendEvaluationResult(result)
                        }) {
                            Text("提交")
                        }
                    }
                }
            }
        }
    }
}
