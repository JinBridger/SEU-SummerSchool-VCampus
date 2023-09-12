package app.vcampus.client

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.vcampus.client.scene.*
import app.vcampus.client.scene.components.NavRail
import app.vcampus.client.scene.components.enterAnimation
import app.vcampus.client.scene.components.sarasaTypography
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition


/**
 * main app UI, navigation logic.
 *
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun App() {
    val navigator = rememberNavigator()
    val currentPos by navigator.currentEntry.collectAsState(initial = null)
    MaterialTheme(typography = sarasaTypography) {
        Row(modifier = Modifier.background(Color(0xFFEEEEEE))) {
            if (currentPos != null) {
                if (currentPos!!.path != "/login")
                    NavRail(
                        navigator = navigator,
                        currentPos = currentPos!!.path
                    )
            }
            NavHost(
                navigator = navigator,
                initialRoute = "/login",
                navTransition = NavTransition(
                    createTransition = fadeIn(),
                    destroyTransition = fadeOut(),
                    pauseTransition = fadeOut(),
                    resumeTransition = fadeIn(),
                    enterTargetContentZIndex = 0f,
                    exitTargetContentZIndex = 0f,
                )
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

                scene("/finance") {
                    enterAnimation {
                        FinanceScene(navigator)
                    }
                }

                scene("/admin") {
                    enterAnimation {
                        AdminScene(navigator)
                    }
                }

                scene("/GPT") {
                    enterAnimation {
                        GPTScene(navigator)
                    }
                }
            }
        }
    }
}