package app.vcampus.client

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import app.vcampus.client.scene.*
import app.vcampus.client.scene.components.NavRail
import app.vcampus.client.scene.components.enterAnimation
import app.vcampus.client.scene.components.sarasaTypography
import app.vcampus.client.viewmodel.MainPanelViewModel
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun App() {
    val navigator = rememberNavigator()
    val currentPos by navigator.currentEntry.collectAsState(initial = null)
    MaterialTheme(typography = sarasaTypography) {
        Row(modifier = Modifier.background(Color(0xFFEEEEEE))) {
            if (currentPos != null) {
                if (currentPos!!.path != "/login")
                    NavRail(navigator = navigator, currentPos = currentPos!!.path)
            }
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
                    enterAnimation {
                        MainPanelScene(navigator)
                    }
                }

                scene("/student_status") {
                    enterAnimation {
                        StudentStatusScene(navigator)
                    }
                }

                scene("/teaching_affairs") {
                    enterAnimation {
                        TeachingAffairsScene(navigator)
                    }
                }

                scene("/library") {
                    enterAnimation {
                        LibraryScene(navigator)
                    }
                }

                scene("/shop") {
                    enterAnimation {
                        ShopScene(navigator)
                    }
                }
            }
        }
    }
}