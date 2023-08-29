package app.vcampus.client

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import app.vcampus.client.scene.LoginScene
import app.vcampus.client.scene.MainPanelScene
import app.vcampus.client.scene.StudentStatusScene
import app.vcampus.client.scene.components.sarasaTypography
import app.vcampus.client.viewmodel.MainPanelViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun App() {
    val navigator = rememberNavigator()
    MaterialTheme(typography = sarasaTypography) {
        NavHost(
            navigator = navigator,
            initialRoute = "/login",
        ) {
            scene("/login") {
                LoginScene(onLogin = {
                    navigator.navigate("/home")
                })
            }

            scene("/home") {
                MainPanelScene(navigator)
            }

            scene("/student_status") {
                StudentStatusScene(navigator)
            }
        }
    }
}