package app.vcampus.client.scene.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.vcampus.server.entity.TeachingClass
import java.io.File

/**
 * enter score list item component, used in `EnterScoreSubscene`
 *
 * @param tc the teaching class
 * @param exportTemplate the xlsx export function
 * @param importScore the xlsx import function
 */
@Composable
fun enterScoreListItem(
    tc: TeachingClass,
    exportTemplate: (TeachingClass, File) -> Unit,
    importScore: (TeachingClass, File) -> Unit
) {
    var showTemplateDialog by remember { mutableStateOf(false) }
    var showImportDialog by remember { mutableStateOf(false) }

    if (showTemplateDialog) {
        FileDialog(
            title = "下载成绩模板",
            mode = FileDialogMode.SAVE,
            saveFileName = "${tc.course.courseName}-${tc.uuid}.xlsx",
            onResult = {
                exportTemplate(tc, it)
                showTemplateDialog = false
            },
            onClose = {
                showTemplateDialog = false
            }
        )
    }

    if (showImportDialog) {
        FileDialog(
            title = "导入成绩",
            mode = FileDialogMode.LOAD,
            loadExtension = "xlsx",
            onResult = {
                importScore(tc, it)
                showImportDialog = false
            },
            onClose = {
                showImportDialog = false
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
                TextButton(onClick = {
                    showTemplateDialog = true
                }) {
                    Text("下载导入模板")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    showImportDialog = true
                }) {
                    Text("从文件导入成绩")
                }
            }
        }
    }
}