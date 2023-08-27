package app.vcampus.client

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import app.vcampus.client.net.NettyHandler
import app.vcampus.client.repository.FakeRepository
import moe.tlaster.precompose.PreComposeWindow

fun main(handler: NettyHandler) {
    FakeRepository.setHandler(handler)
    application {
        PreComposeWindow(
            title = "PreCompose Sample",
            onCloseRequest = {
                exitApplication()
            },
            state = WindowState(size = DpSize(1400.dp, 800.dp))
        ) {
            App()
        }
    }
}