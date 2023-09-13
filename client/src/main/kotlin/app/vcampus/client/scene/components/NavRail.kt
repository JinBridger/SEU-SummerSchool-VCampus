package app.vcampus.client.scene.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.vcampus.client.Navis
import app.vcampus.client.repository.FakeRepository
import moe.tlaster.precompose.navigation.Navigator

/**
 * nav rail component, the left part of UI
 *
 * @param navigator the navigator of whole app
 * @param currentPos current position of navigator
 */
@Composable
fun NavRail(navigator: Navigator, currentPos: String) {
    NavigationRail {
        Navis.forEach {
            if (it.permission.intersect(
                    FakeRepository.user.roles.toList()
                ).isNotEmpty() || it.permission.contains("user")
            )
                NavigationRailItem(
                    selected = currentPos == it.path,
                    onClick = {
                        if (currentPos != it.path) navigator.navigate(
                            it.path
                        )
                    },
                    icon = { Icon(it.icon, "") },
                    label = { Text(it.name) },
                    alwaysShowLabel = false
                )
        }
        Spacer(Modifier.weight(1F))
        NavigationRailItem(
            selected = false,
            onClick = {
                FakeRepository.isConnected = false
                navigator.navigate("/login")
            },
            icon = { Icon(Icons.Default.ExitToApp, "") }
        )
    }
}