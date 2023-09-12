package app.vcampus.client.scene.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class SideBarItem(
    val isLabel: Boolean, val heading: String, val caption: String,
    val icon: ImageVector, val isChosen: Boolean
)


/**
 * the side bar item, the left part of UI
 *
 * @param sideBarItems the list of side bar items
 * @param onClick function when click item
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SideBar(
    sideBarItems: List<SideBarItem> = listOf(
        SideBarItem(false, "", "", Icons.Default.Person, false)
    ),
    onClick: (index: Int) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxWidth(0.25F)) {
        LazyColumn(state = rememberLazyListState()) {
            item {
                sideBarItems.forEach {
                    if (!it.isLabel) {
                        if (it.isChosen) {
                            ListItem(
                                modifier = Modifier.background(
                                    MaterialTheme.colors.primary
                                ),
                                secondaryText = {
                                    Text(it.caption, color = Color.White)
                                },
                                icon = {
                                    Icon(it.icon, "", tint = Color.White)
                                },
                            ) {
                                Text(it.heading, color = Color.White)
                            }
                        } else {
                            ListItem(
                                modifier = Modifier.clickable {
                                    onClick(sideBarItems.indexOf(it))
                                },
                                secondaryText = { Text(it.caption) },
                                icon = {
                                    Icon(it.icon, "", tint = Color.DarkGray)
                                },
                            ) {
                                Text(it.heading)
                            }
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
            }
        }
    }
}