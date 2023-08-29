package app.vcampus.client.scene.components

import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import app.vcampus.client.Navis
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun NavRail(navigator: Navigator, currentPos: String) {
    NavigationRail {
        Navis.forEach {
            NavigationRailItem(
                selected = currentPos == it.path,
                onClick = { navigator.navigate(it.path) },
                icon = { Icon(it.icon, "") },
                label = { Text(it.name) },
                alwaysShowLabel = false
            )
        }
    }
}