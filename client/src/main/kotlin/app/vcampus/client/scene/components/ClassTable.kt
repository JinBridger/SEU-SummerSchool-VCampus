package app.vcampus.client.scene.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.vcampus.client.viewmodel.TeachingAffairsViewModel
import app.vcampus.server.entity.TeachingClass

/**
 * class item component, used in `ClassTable`
 *
 * @param className the name of class
 * @param teacherName the name of teacher
 * @param position the position of class
 */
@Composable
fun classItem(className: String, teacherName: String, position: String) {
    val hashCode = (className + teacherName).hashCode().mod(6)
    val colors = listOf(
        Color(0xff72b8a0), Color(0xff7fc882), Color(0xff6296d1),
        Color(0xff8b5ead), Color(0xffe5c95c), Color(0xffc85f4b)
    )
    Card(
        modifier = Modifier.fillMaxSize().padding(8.dp).shadowCustom(
            blurRadius = 2.dp, shapeRadius = 2.dp
        ),
        backgroundColor = colors[hashCode]
    ) {
        Column(Modifier.padding(6.dp)) {
            Text(className, color = Color.White)
            Text(teacherName, color = Color.White)
            Text(position, color = Color.White)
        }
    }
}

/**
 * check if given position has a class
 *
 * @param allClass the list of all classes
 * @param weekday which day
 * @param section which section
 * @return the class in this position, return `null` if there's no class
 */
fun isHereClass(
    allClass: MutableList<TeachingClass>, weekday: Int,
    section: Int,
    week: Int = 1
): TeachingClass? {
    allClass.forEach { tc ->
        tc.schedule.forEach {
            if (week >= it.first.first && week <= it.first.second && it.second.first == weekday) {
                if (it.second.second.first <= section && section <= it.second.second.second) {
                    return tc
                }
            }
        }
    }
    return null
}

/**
 * class table component, used in `MyScheduleSubscene`
 * draw the whole class table
 *
 * @param viewModel the viewmodel of the subscene
 */
@Composable
fun classTable(viewModel: TeachingAffairsViewModel, currentWeek: Int = 1) {
    Box(Modifier.fillMaxWidth().height(700.dp)) {
        // TABLE
        Row(Modifier.fillMaxSize()) {
            Column(Modifier.weight(1F).fillMaxHeight()) {
                Divider(
                    Modifier.fillMaxWidth(), thickness = 1.dp,
                    color = Color.LightGray
                )
                Row(Modifier.weight(1F).fillMaxWidth()) {}
                (1..13).forEach {
                    Divider(
                        Modifier.fillMaxWidth(), thickness = 1.dp,
                        color = Color.LightGray
                    )
                    Row(
                        Modifier.weight(1F).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("第 $it 节")
                    }
                }
                Divider(
                    Modifier.fillMaxWidth(), thickness = 1.dp,
                    color = Color.LightGray
                )
            }

            (1..7).forEach { weekday ->
                Column(Modifier.weight(1F).fillMaxHeight()) {
                    if (weekday != 0) {
                        Divider(
                            Modifier.fillMaxWidth(), thickness = 1.dp,
                            color = Color.LightGray
                        )
                        Row(
                            Modifier.weight(1F).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                when (weekday) {
                                    1 -> "星期一"
                                    2 -> "星期二"
                                    3 -> "星期三"
                                    4 -> "星期四"
                                    5 -> "星期五"
                                    6 -> "星期六"
                                    7 -> "星期日"
                                    else -> "?"
                                }
                            )
                        }
                    } else {
                        Divider(
                            Modifier.fillMaxWidth(), thickness = 1.dp,
                            color = Color.LightGray
                        )
                        Row(Modifier.weight(1F).fillMaxWidth()) {}
                    }
                    (1..13).forEach {
                        Divider(
                            Modifier.fillMaxWidth(), thickness = 1.dp,
                            color = Color.LightGray
                        )
                        Row(Modifier.weight(1F).fillMaxWidth()) {

                        }
                    }
                    Divider(
                        Modifier.fillMaxWidth(), thickness = 1.dp,
                        color = Color.LightGray
                    )
                }
            }
        }
        // CLASS
        Row(Modifier.fillMaxSize()) {
            Column(Modifier.weight(1F).fillMaxHeight()) {
                Divider(
                    Modifier.fillMaxWidth(), thickness = 1.dp,
                    color = Color.Transparent
                )
                Row(Modifier.weight(1F).fillMaxWidth()) {}
                (1..13).forEach {
                    Divider(
                        Modifier.fillMaxWidth(), thickness = 1.dp,
                        color = Color.Transparent
                    )
                    Row(
                        Modifier.weight(1F).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                    }
                }
                Divider(
                    Modifier.fillMaxWidth(), thickness = 1.dp,
                    color = Color.Transparent
                )
            }

            (1..7).forEach { weekday ->
                Column(Modifier.weight(1F).fillMaxHeight()) {
                    Divider(
                        Modifier.fillMaxWidth(), thickness = 1.dp,
                        color = Color.Transparent
                    )
                    Row(Modifier.weight(1F).fillMaxWidth()) {
                    }
                    var index = 1
                    while (index <= 13) {
                        val classHere = isHereClass(
                            viewModel.myClasses.selected,
                            weekday, index, currentWeek
                        )
                        if (classHere != null) {
                            Box(
                                Modifier.fillMaxWidth().weight(
                                    (classHere.schedule[0].second.second.second - classHere.schedule[0].second.second.first + 1).toFloat()
                                )
                            )
                            {
                                classItem(
                                    classHere.course.courseName,
                                    classHere.teacherName,
                                    classHere.place
                                )
                            }
                            (1..(classHere.schedule[0].second.second.second - classHere.schedule[0].second.second.first + 1)).forEach {
                                Divider(
                                    Modifier.fillMaxWidth(),
                                    thickness = 1.dp,
                                    color = Color.Transparent
                                )
                            }
                            index += (classHere.schedule[0].second.second.second - classHere.schedule[0].second.second.first + 1)
                        } else {
                            Divider(
                                Modifier.fillMaxWidth(), thickness = 1.dp,
                                color = Color.Transparent
                            )
                            Row(Modifier.weight(1F).fillMaxWidth()) {
                            }
                            index += 1
                        }
                    }
                }
            }
        }
    }
}