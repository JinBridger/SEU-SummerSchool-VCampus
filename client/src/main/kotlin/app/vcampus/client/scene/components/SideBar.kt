package app.vcampus.client.scene.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SideBar() {
    Box(modifier = Modifier.fillMaxWidth(0.25F)) {
        LazyColumn {
            item {
                (0..10).toList().forEach {
                    ListItem(secondaryText = {
                        Text("Text")
                    }, icon = { Icon(Icons.Default.TextFields, "") }) {
                        Text("TestText")
                    }
                    Divider()
                }
            }
        }
    }
}