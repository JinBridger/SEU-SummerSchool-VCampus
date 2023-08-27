package app.vcampus.client.scene.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.vcampus.client.Navis
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun navDrawerItem(text: String, icon:ImageVector, onClick: () -> Unit) {
    ListItem(
        Modifier.clickable { onClick() },
        text = { Text(text) },
        icon = {Icon(
            icon,
            contentDescription = null
        )}
    )
}

@Composable
fun navDrawer(navigator: Navigator) {
    Column {
        Box(Modifier.height(200.dp)) {  }
        LazyColumn(Modifier.fillMaxHeight()) {
            item {
                Navis.forEach {
                    navDrawerItem(it.name, it.icon) {
                        navigator.navigate(it.path)
                    }
                }
            }
        }
    }

}