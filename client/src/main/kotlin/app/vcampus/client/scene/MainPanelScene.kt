package app.vcampus.client.scene

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.MainPanelViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel


@ExperimentalMaterialApi
@Composable
fun MainPanelScene(
        navi: Navigator,
) {
    val viewModel = viewModel(MainPanelViewModel::class, listOf()) {
        MainPanelViewModel()
    }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

//    Scaffold(
//        scaffoldState = scaffoldState,
//        topBar = {
//            TopBar("欢迎来到自助服务大厅！")
//        }
//    ) {
//        Row {
//            NavRail(navi, "/home")

    Box(Modifier.fillMaxSize()) {
        Box(Modifier.width(800.dp).align(Alignment.TopCenter)) {
            LazyColumn {
                item {
                    Spacer(Modifier.height(50.dp))
                    pageTitle("晚上好，${FakeRepository.user.name}",
                            "今天想做些什么？")
                }
            }
        }
    }
//        }
//    }
}