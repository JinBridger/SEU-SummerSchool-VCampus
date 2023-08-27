package app.vcampus.client

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import app.vcampus.client.scene.LoginScene
import app.vcampus.client.scene.MainPanelScene
import app.vcampus.client.viewmodel.MainPanelViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun App() {
    val navigator = rememberNavigator()
    MaterialTheme {
        NavHost(
            navigator = navigator,
            initialRoute = "/login",
        ) {
            scene("/login") {
//                MainPanelScene(
//                    id = 1,
//                    onEdit = {
////                        navigator.navigate("/edit/$it")
//                    },
//                    onBack = {
////                        navigator.goBack()
//                    },
//                )
                LoginScene(onLogin = {
                    navigator.navigate("/home")
                })
            }

            scene("/home") {
                MainPanelScene(
                    id = 1,
                    onEdit = {},
                    onBack = {}
                )
            }
        }
    }
}