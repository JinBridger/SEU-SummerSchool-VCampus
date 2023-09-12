package app.vcampus.client.scene.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import javafx.embed.swing.JFXPanel

/**
 * compose JavaFX Panel, used it to embed the webview to show GPT
 *
 * @param panel the JFX Panel
 */
@Composable
fun ComposeJFXPanel(panel: JFXPanel) {
    SwingPanel(
        modifier = Modifier.fillMaxSize(),
        factory = { panel }
    )
}