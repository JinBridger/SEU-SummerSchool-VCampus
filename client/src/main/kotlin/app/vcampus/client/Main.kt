package app.vcampus.client

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.window.application
import moe.tlaster.precompose.PreComposeWindow

@OptIn(ExperimentalMaterialApi::class)
fun main() {
    application {
        PreComposeWindow(
            title = "PreCompose Sample",
            onCloseRequest = {
                exitApplication()
            },
        ) {
            App()
        }
    }
}