package app.vcampus.client.scene

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import app.vcampus.client.scene.components.Browser
import app.vcampus.client.viewmodel.MainPanelViewModel
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import netscape.javascript.JSObject


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
        Browser()
    }
}