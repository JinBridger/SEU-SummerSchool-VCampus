package app.vcampus.client

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NaviItem(
    val name: String,
    val path: String,
    val icon: ImageVector
)

val Navis = arrayListOf(
    NaviItem("主页", "/home", Icons.Default.Home),
    NaviItem("学籍管理", "/student_status", Icons.Default.Person)
)