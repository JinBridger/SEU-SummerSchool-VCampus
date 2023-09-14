package app.vcampus.client

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.TopBar
import javafx.application.Platform
import moe.tlaster.precompose.PreComposeWindow

/**
 * Where client app starts
 *
 */
fun main() {
    Platform.setImplicitExit(false)
    FakeRepository.initGptWebview()

    application {
        val state = rememberWindowState(size = DpSize(1400.dp, 800.dp))
        PreComposeWindow(
            title = "PreCompose Sample",
            onCloseRequest = {
                exitApplication()
            },
            state = state,
            undecorated = true,
        ) {
            FakeRepository.window = window
            Column {
                WindowDraggableArea {
                    TopBar("VCampus") { exitApplication() }
                }
                App()
            }
        }
    }
}