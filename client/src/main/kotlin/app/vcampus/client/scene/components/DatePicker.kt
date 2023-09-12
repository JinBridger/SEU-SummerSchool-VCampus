/*
    Composable DatePicker for Compose Desktop and other platforms.
    How to use:
      var showDatePicker by remember { mutableStateOf(false) }
      var selectedDate by remember { mutableStateOf(Date()) }

      //implement here the logic to show datepicker and use de return value

      if (showDatePicker) {
           DatePicker(
              initDate = Date(),
              onDismissRequest = { showDatePicker = false },
              onDateSelect = {
                  selectedDate = it
                  showDatePicker = false
              }
           )
       }
*/

package app.vcampus.client.scene.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.*


/**
 * date picker component, however never used in this project
 * for its usage please refer to `DatePicker.kt`
 *
 * @param initDate the initial date when open date picker
 * @param onDateSelect function when confirm the date
 * @param onDismissRequest idk
 * @param minYear the minimal year could be selected
 * @param maxYear the maximum year could be selected
 */
@Composable
fun DatePicker(
    initDate: Date = Date(),
    onDateSelect: (Date) -> Unit,
    onDismissRequest: () -> Unit,
    minYear: Int = GregorianCalendar().get(Calendar.YEAR) - 10,
    maxYear: Int = GregorianCalendar().get(Calendar.YEAR) + 10
) {
    val calendar = GregorianCalendar().apply {
        time = initDate
    }

    var year by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var month by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    var day by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    EmptyBaseDialog(
        onDismissRequest = onDismissRequest
    ) {
        MaterialTheme {
            Card(
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {

                    Box(
                        modifier = Modifier.background(
                            MaterialTheme.colors.primary
                        )
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = SimpleDateFormat(
                                "yyyy 年 MM 月 d 日"
                            ).format(
                                GregorianCalendar(
                                    year, month,
                                    day
                                ).time
                            ),
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.h6
                        )
                    }

                    //Días
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {


                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {

                            MonthSelector(
                                month = month,
                                onValueChange = { month = it }
                            )

                            YearSelector(
                                year = year,
                                onValueChange = { year = it },
                                minYear = minYear,
                                maxYear = maxYear,
                                modifier = Modifier.align(
                                    Alignment.CenterEnd
                                )
                            )

                        }

                        Header("日", "一", "二", "三", "四", "五", "六")

                        val startDay = GregorianCalendar(year, month, 1)
                            .apply {
                                firstDayOfWeek = Calendar.SUNDAY
                            }.get(Calendar.DAY_OF_WEEK)

                        var render = false
                        var dayCounter = 1
                        val maxDay = GregorianCalendar(
                            year, month,
                            1
                        ).daysCount()

                        if (day > maxDay) {
                            day = maxDay
                        }

                        for (i in 1..6) {
                            Row {
                                for (j in 1..7) {
                                    if (j == startDay) {
                                        render = true
                                    }
                                    if (!render || dayCounter > maxDay) {
                                        Day(0, false) { }
                                    } else {
                                        Day(
                                            day = dayCounter,
                                            selected = (day == dayCounter),
                                            onChangeValue = { day = it }
                                        )
                                        dayCounter++
                                    }
                                }
                            }
                        }

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = { onDismissRequest() }
                        ) {
                            Text(
                                text = "取消"
                            )
                        }

                        TextButton(
                            onClick = {
                                onDateSelect(
                                    GregorianCalendar(
                                        year, month,
                                        day
                                    ).time
                                )
                            }
                        ) {
                            Text(
                                text = "确定"
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun YearSelector(
    year: Int,
    onValueChange: (Int) -> Unit,
    minYear: Int,
    maxYear: Int,
    modifier: Modifier = Modifier
) {
    var expandYearList by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable { expandYearList = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = year.toString())
        Spacer(Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null
        )

        DropdownMenu(
            expanded = expandYearList,
            onDismissRequest = { expandYearList = false }
        ) {
            for (y in minYear..maxYear) {
                DropdownMenuItem(onClick = {
                    onValueChange(y)
                    expandYearList = false
                }) {
                    Text(text = y.toString())
                }
            }
        }
    }
}

@Composable
fun MonthSelector(
    month: Int,
    onValueChange: (Int) -> Unit
) {
    var expandMonthList by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.clickable { expandMonthList = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = monthName(month).uppercase())
        Spacer(Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null
        )

        DropdownMenu(
            expanded = expandMonthList,
            onDismissRequest = { expandMonthList = false }
        ) {
            for (m in Calendar.JANUARY..Calendar.DECEMBER) {
                DropdownMenuItem(onClick = {
                    onValueChange(m)
                    expandMonthList = false
                }) {
                    Text(text = monthName(m).uppercase())
                }
            }
        }
    }
}

@Composable
fun Header(vararg daysNames: String) {
    Row {
        for (dayName in daysNames) {
            DayName(dayName)
        }
    }
}

@Composable
fun Day(
    day: Int,
    selected: Boolean,
    onChangeValue: (Int) -> Unit
) {
    val clickable = day != 0

    Box(
        modifier = Modifier.size(48.dp)
            .padding(3.dp)
            .clip(RoundedCornerShape(50))
            .background(
                if (selected) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.surface
                }
            ).clickable(enabled = clickable) { onChangeValue(day) },
        contentAlignment = Alignment.Center
    ) {
        if (day != 0) {
            Text(
                text = day.toString(),
                color = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun DayName(day: String) {
    Box(
        modifier = Modifier.size(48.dp)
            .padding(3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day,
            color = MaterialTheme.colors.primary
        )
    }
}

fun monthName(month: Int): String {
    return when (month) {
        GregorianCalendar.JANUARY -> "一月"
        GregorianCalendar.FEBRUARY -> "二月"
        GregorianCalendar.MARCH -> "三月"
        GregorianCalendar.APRIL -> "四月"
        GregorianCalendar.MAY -> "五月"
        GregorianCalendar.JUNE -> "六月"
        GregorianCalendar.JULY -> "七月"
        GregorianCalendar.AUGUST -> "八月"
        GregorianCalendar.SEPTEMBER -> "九月"
        GregorianCalendar.OCTOBER -> "十月"
        GregorianCalendar.NOVEMBER -> "十一月"
        GregorianCalendar.DECEMBER -> "十二月"
        else -> "Undefined"
    }
}

fun GregorianCalendar.daysCount(): Int {
    return when (get(Calendar.MONTH)) {
        Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
        Calendar.FEBRUARY -> if (isLeapYear(get(Calendar.YEAR))) 29 else 28
        Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
        else -> 0
    }
}

private val TextPadding = Modifier.padding(0.dp)

@Composable
fun EmptyBaseDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        AlertDialogContent2(
            modifier = modifier.width(IntrinsicSize.Min),
            content = content,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    }
}

@Composable
internal fun AlertDialogContent2(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor
    ) {
        Column {
            AlertDialogBaselineLayout2(
                content = content
            )
        }
    }
}

@Composable
internal fun ColumnScope.AlertDialogBaselineLayout2(
    content: @Composable (() -> Unit)
) {
    Layout(
        {
            Box(TextPadding.layoutId("content").align(Alignment.Start)) {
                content()
            }
        },
        Modifier.weight(1f, false)
    ) { measurables, constraints ->

        val textPlaceable = measurables.firstOrNull { it.layoutId == "content" }?.measure(
            constraints.copy(minHeight = 0)
        )

        val layoutWidth = textPlaceable?.width ?: 0

        val layoutHeight = textPlaceable?.let {
            textPlaceable.height
        } ?: 0

        layout(layoutWidth, layoutHeight) {
            textPlaceable?.place(0, 0)
        }
    }
}