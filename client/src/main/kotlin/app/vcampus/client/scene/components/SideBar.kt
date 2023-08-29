package app.vcampus.client.scene.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class SideBarItem(
    val isLabel: Boolean, val heading: String, val caption: String, val icon: ImageVector
)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SideBar(sideBarItems: List<SideBarItem> = listOf(SideBarItem(false, "", "", Icons.Default.Person))) {
    Box(modifier = Modifier.fillMaxWidth(0.25F)) {
        LazyColumn {
            item {
                sideBarItems.forEach {
                    if (!it.isLabel) {
                        ListItem(
                            secondaryText = { Text(it.caption) },
                            icon = { Icon(it.icon, "", tint = Color.DarkGray) },
                        ) {
                            Text(it.heading)
                        }

                        Divider()
                    } else {
                        Spacer(modifier = Modifier.height(16.dp))

                        Row {
                            Spacer(modifier = Modifier.width(18.dp))
                            Text(
                                text = it.heading,
                                fontWeight = FontWeight(700),
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Divider()
                    }
                }
//                (0..10).toList().forEach {
//                    ListItem(secondaryText = {
//                        Text("Text")
//                    }, icon = { Icon(Icons.Default.TextFields, "") }) {
//                        Text("TestText")
//                    }
//                    Divider()
//                }
            }
        }
    }
}