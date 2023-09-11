package app.vcampus.client.scene.components

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.input.key.Key.Companion.Window
import androidx.compose.ui.window.Window
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import javax.swing.SwingUtilities

@Composable
fun Browser() {
    val jfxPanel = JFXPanel()
    var engine: WebEngine? = null

    LaunchedEffect(Unit) {
        Platform.setImplicitExit(false);
        Platform.runLater {
            val view = WebView()
            engine = view.engine

            jfxPanel.scene = Scene(view)
            engine?.load("https://gpt.seumsc.com")
        }
    }

//    DisposableEffect(Unit) {
//        onDispose {
//            Platform.exit()
//        }
//    }

    SwingPanel(
        modifier = Modifier.fillMaxSize(),
        factory = { jfxPanel }
    )
}