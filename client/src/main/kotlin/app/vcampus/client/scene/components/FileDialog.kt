package app.vcampus.client.scene.components

import androidx.compose.runtime.Composable
import java.io.File

enum class FileDialogMode { LOAD, SAVE }

@Composable
fun FileDialog(
    title: String,
    mode: FileDialogMode = FileDialogMode.LOAD,
    saveFileName: String = "file",
    onResult: (result: File) -> Unit,
    onClose: () -> Unit
) {
    val awtMode = if (mode == FileDialogMode.LOAD) java.awt.FileDialog.LOAD else java.awt.FileDialog.SAVE
    val fileChooser = java.awt.FileDialog(java.awt.Frame(), title, awtMode)

    if (mode == FileDialogMode.SAVE) {
        fileChooser.file = saveFileName
    }

    fileChooser.isVisible = true

    if (fileChooser.file != null) {
        val file = File(fileChooser.directory + fileChooser.file)
        onResult(file)
    } else {
        onClose()
    }

    fileChooser.isVisible = false
    fileChooser.dispose()
}