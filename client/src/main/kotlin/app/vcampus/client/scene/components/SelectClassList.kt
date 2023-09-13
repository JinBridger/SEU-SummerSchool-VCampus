package app.vcampus.client.scene.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.client.viewmodel.TeachingAffairsViewModel
import app.vcampus.server.entity.Course
import app.vcampus.server.entity.TeachingClass

fun getIsChosenTeachingClass(
    viewModel: TeachingAffairsViewModel,
    teachingClass: TeachingClass
): Boolean {
    viewModel.myClasses.selected.forEach {
        if (it.uuid == teachingClass.uuid) {
            return true
        }
    }
    return false
}

fun getIsConflict(viewModel: TeachingAffairsViewModel, teachingClass: TeachingClass): Boolean {
    viewModel.myClasses.selected.forEach {
        val thisSchedule = teachingClass.schedule
        val itSchedule = it.schedule
        thisSchedule.forEach { thisPair ->
            itSchedule.forEach { itPair ->
                if (thisPair.second.first == itPair.second.first) {
                    if (!(thisPair.second.second.first > itPair.second.second.second || thisPair.second.second.second < itPair.second.second.first)) {
                        return true
                    }
                }
            }
        }
    }
    return false
}

/**
 * teaching class card of select class list item
 *
 * @param viewModel the viewmodel of subscene
 * @param teachingClass the teaching class
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun selectClassCard(
    viewModel: TeachingAffairsViewModel,
    teachingClass: TeachingClass
) {
    val isChosen = remember { mutableStateOf(false) }
    val isConflict = remember { mutableStateOf(false) }
    val scheduleString = remember { mutableStateOf("") }

    isChosen.value = getIsChosenTeachingClass(viewModel, teachingClass)
    isConflict.value = getIsConflict(viewModel, teachingClass)
    scheduleString.value = teachingClass.humanReadableSchedule()


    Surface(
        modifier = Modifier.border(
            1.dp, color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
        ).width(250.dp).padding(10.dp)
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(teachingClass.teacherName, fontWeight = FontWeight(700))
                Spacer(Modifier.width(16.dp))
                Column {
                    if (isChosen.value) {
                        Chip(onClick = {}, modifier = Modifier.height(22.dp),
                            border = ChipDefaults.outlinedBorder,
                            colors = ChipDefaults.outlinedChipColors(
                                Color(0xff508e54)
                            ), content = {
                                Text("已选", color = Color.White)
                            })
                    } else {
                        if (isConflict.value) {
                            Chip(onClick = {},
                                modifier = Modifier.height(22.dp),
                                border = ChipDefaults.outlinedBorder,
                                colors = ChipDefaults.outlinedChipColors(
                                    Color(0xffe74c3c)
                                ), content = {
                                    Text("课程冲突", color = Color.White)
                                })
                        }
                    }
                    Spacer(Modifier.height(2.dp))
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(scheduleString.value)
            Text(teachingClass.place)
            Spacer(Modifier.height(10.dp))
            Row {
                Text("教学班容量：", fontWeight = FontWeight(700))
                Text("${teachingClass.capacity}人")
            }
            Row {
                Text("已选人数：", fontWeight = FontWeight(700))
                Text("${teachingClass.selectedCount}人")
            }
            Spacer(Modifier.weight(1F))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (isChosen.value) {
                    Button(onClick = {
                        viewModel.myClasses.dropClass(teachingClass.uuid)
                    }, enabled = teachingClass.selectRecord?.grade == null) {
                        Text("退选")
                    }
                } else {
                    Button(onClick = {
                        viewModel.myClasses.chooseClass(teachingClass.uuid)
                    }, enabled = !(isConflict.value || teachingClass.selectedCount >= teachingClass.capacity)) {
                        Text("选择")
                    }
                }
            }
        }
    }
}

fun getIsChosenCourse(viewModel: TeachingAffairsViewModel, course: Course): Boolean {
    viewModel.myClasses.selected.forEach {
        if (it.courseUuid == course.uuid) {
            return true
        }
    }
    return false
}


/**
 * select class list item component, used in `ChooseClassSubscene`
 *
 * @param viewModel the viewmodel of subscene
 * @param course the course of list item
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun selectClassListItem(viewModel: TeachingAffairsViewModel, course: Course) {
    val isChosen = remember { mutableStateOf(false) }
    isChosen.value = getIsChosenCourse(viewModel, course)

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
    ),
        onClick = { expanded = !expanded }) {
        Box(Modifier.fillMaxSize().padding(10.dp)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxHeight()) {
                        Row {
                            Text(
                                text = course.courseName,
                                fontWeight = FontWeight(700),
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = course.courseId, color = Color.DarkGray)
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Spacer(Modifier.height(4.dp))
                        Row {
                            Chip(onClick = {},
                                modifier = Modifier.height(22.dp),
                                border = ChipDefaults.outlinedBorder,
                                colors = ChipDefaults.outlinedChipColors(
                                    Color(0xff16a085)
                                ), content = {
                                    Text("限选", color = Color.White)
                                })
                            Spacer(modifier = Modifier.width(4.dp))
                            Chip(onClick = {},
                                modifier = Modifier.height(22.dp),
                                border = ChipDefaults.outlinedBorder,
                                colors = ChipDefaults.outlinedChipColors(
                                    Color(0xff3498db)
                                ), content = {
                                    Text(
                                        "学分: ${
                                            String.format("%.2f", course.credit / 1.0)
                                        } ", color = Color.White
                                    )
                                })
                            Spacer(modifier = Modifier.width(4.dp))
                            Chip(onClick = {},
                                modifier = Modifier.height(22.dp),
                                border = ChipDefaults.outlinedBorder,
                                colors = ChipDefaults.chipColors(
                                    Color(0xff34495e)
                                ), content = {
                                    Text("首修", color = Color.White)
                                })
                        }
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    if (isChosen.value) {
                        Icon(Icons.Default.Done, "")
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "已选",
                            fontWeight = FontWeight(700),
                        )
                    }
                }

                if (expanded) {
                    val state = rememberLazyListState()
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                    LazyRow(Modifier.fillMaxWidth(), state) {
                        course.teachingClasses.forEach {
                            item(it.uuid) {
                                selectClassCard(viewModel, it)
                                Spacer(Modifier.width(10.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    HorizontalScrollbar(
                        modifier = Modifier.fillMaxWidth().padding(
                            end = 12.dp
                        ),
                        adapter = rememberScrollbarAdapter(
                            scrollState = state
                        )
                    )
                }
            }
        }
    }
}