package app.vcampus.client.scene

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.MainPanelViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import java.time.LocalTime

/**
 * main panel scene
 *
 * @param navi navigator
 */
@ExperimentalMaterialApi
@Composable
fun MainPanelScene(
    navi: Navigator,
) {
    val viewModel = viewModel(MainPanelViewModel::class, listOf()) {
        MainPanelViewModel()
    }

    Box(Modifier.fillMaxSize()) {
        Box(Modifier.width(800.dp).align(Alignment.Center)) {
            LazyColumn {
                item {
                    pageTitle(
                        "${viewModel.greetings}，${FakeRepository.user.name}！",
                        "今天想做些什么？"
                    )
                    Spacer(Modifier.height(150.dp))
                }
            }
        }
    }
}