package app.vcampus.client.scene.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.server.entity.TeachingClass
import java.io.File

/**
 * my class list item component, used in `MyClassSubscene`
 *
 * @param tc the teaching class
 * @param saveCallback function when save xlsx
 */
@Composable
fun myClassListItem(tc: TeachingClass, saveCallback: (TeachingClass, File) -> Unit) {
    var showFileDialog by remember { mutableStateOf(false) }

    if (showFileDialog) {
        FileDialog(
            title = "导出学生名单",
            mode = FileDialogMode.SAVE,
            saveFileName = "${tc.course.courseName}-${tc.uuid}.xlsx",
            onResult = {
                saveCallback(tc, it)
                showFileDialog = false
            },
            onClose = {
                showFileDialog = false
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxWidth().border(
            1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        Box(Modifier.fillMaxSize().padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.fillMaxHeight()) {
                            Row {
                                Text(
                                    text = tc.course.courseName,
                                    fontWeight = FontWeight(700)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = tc.course.courseId,
                                    color = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(tc.humanReadableSchedule())
                            Text(tc.place)
                        }
                    }
                }
                Spacer(Modifier.weight(1F))
                Button(onClick = {
                    showFileDialog = true
                }) {
                    Text("导出学生名单")
                }
            }
        }
    }
}