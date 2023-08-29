package app.vcampus.client.scene.components

import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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