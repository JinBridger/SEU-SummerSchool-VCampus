package app.vcampus.client.scene

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.ComposeJFXPanel
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun GPTScene(
    navi: Navigator,
) {
    Box(Modifier.fillMaxSize()) {
        ComposeJFXPanel(FakeRepository.gptJfxPanel)
    }
}