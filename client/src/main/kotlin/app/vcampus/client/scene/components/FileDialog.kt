package app.vcampus.client.scene.components

import androidx.compose.runtime.Composable
import java.io.File

enum class FileDialogMode { LOAD, SAVE }

/**
 * file dialog component, calls when save/load files
 *
 * @param title the title of window
 * @param mode save or load
 * @param saveFileName the name of file to be saved
 * @param loadExtension load file extension filter
 * @param onResult function when confirm
 * @param onClose function when close
 */
@Composable
fun FileDialog(
    title: String,
    mode: FileDialogMode = FileDialogMode.LOAD,
    saveFileName: String = "file",
    loadExtension: String = "",
    onResult: (result: File) -> Unit,
    onClose: () -> Unit
) {
    val awtMode = if (mode == FileDialogMode.LOAD) java.awt.FileDialog.LOAD else java.awt.FileDialog.SAVE
    val fileChooser = java.awt.FileDialog(java.awt.Frame(), title, awtMode)

    if (mode == FileDialogMode.SAVE) {
        fileChooser.file = saveFileName
    }

    if (mode == FileDialogMode.LOAD && loadExtension != "") {
        fileChooser.filenameFilter = java.io.FilenameFilter { _, name ->
            name.endsWith(loadExtension)
        }
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