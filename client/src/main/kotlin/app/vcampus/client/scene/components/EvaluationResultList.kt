package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.server.entity.TeachingClass

/**
 * rating result bar component, shows the ratio of ratings
 *
 * @param evaluateString the evaluation question
 * @param result the ratio of rating results
 */
@Composable
fun ratingResultBar(evaluateString: String, result: List<Int>) {
    val colors = listOf(
        Color(0xffd65745), Color(0xffc45c24), Color(0xffd8833b),
        Color(0xffe7a03c), Color(0xffeac645), Color(0xff65c97a),
        Color(0xff55ac68), Color(0xff58b99d), Color(0xff4a9e86),
        Color(0xff326b5a)
    )
    Column(Modifier.fillMaxWidth()) {
        Text(
            evaluateString,
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(4.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("1")
            Spacer(Modifier.width(10.dp))
            (0..9).forEach {
                if (result[it] != 0)
                    Box(Modifier.weight(result[it].toFloat()).background(colors[it]).height(10.dp))
            }
            Box(Modifier.weight(0.001F).background(Color.LightGray).height(10.dp))
            Spacer(Modifier.width(10.dp))
            Text("10")
        }
        Spacer(Modifier.height(10.dp))
    }
}

/**
 * evaluate result list item component, used in `EvaluateResultSubscene`
 *
 * @param result the teaching class of rating
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun evaluateResultListItem(result: TeachingClass) {
    var expanded by remember { mutableStateOf(false) }
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
                                text = result.course.courseName,
                                fontWeight = FontWeight(700)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = result.course.courseId,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = result.humanReadableSchedule(),
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = result.place,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }

                if (expanded) {
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                    (0..3).forEach {
                        ratingResultBar(evaluateItem[it], result.getEvaluationResult().first[it])
                    }

                    Divider()
                    Spacer(Modifier.height(8.dp))

                    Text("匿名评价：", fontWeight = FontWeight(700))

                    result.getEvaluationResult().second.forEach {
                        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Canvas(modifier = Modifier.padding(start = 8.dp, end = 8.dp).size(6.dp)) {
                                drawCircle(Color.Black)
                            }
                            Text(it)
                        }
                    }
                }
            }
        }
    }
}