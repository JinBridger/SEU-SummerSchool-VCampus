package app.vcampus.client

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class NaviItem(
        val name: String,
        val path: String,
        val icon: ImageVector
)

val Navis = arrayListOf(
        NaviItem("主页", "/home", Icons.Default.Home),
        NaviItem("学籍", "/student_status", Icons.Default.Person),
        NaviItem("教务", "/teaching_affairs", Icons.Default.School),
        NaviItem("图书馆", "/library", Icons.Default.Book),
        NaviItem("超市", "/shop", Icons.Default.LocalMall),
        NaviItem("财务", "/finance", Icons.Default.Savings),
        NaviItem("管理员", "/admin", Icons.Default.ManageAccounts)
)