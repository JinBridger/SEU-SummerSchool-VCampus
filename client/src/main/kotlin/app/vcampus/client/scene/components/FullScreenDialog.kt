package app.vcampus.client.scene.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * full screen dialog, however not used in the project
 *
 * @param onDismissRequest idk
 * @param onConfirmed function when confirm
 */
@Composable
fun FullScreenDialog(
    onDismissRequest: () -> Unit,
    onConfirmed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        shape = MaterialTheme.shapes.large,
        title = {
            Text(text = "Title")
        },
        text = {
            Text(
                "This area typically contains the supportive text " +
                        "which presents the details regarding the Dialog's purpose."
            )
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onConfirmed() }
                ) {
                    Text("Dismiss")
                }
            }
        }
    )

}