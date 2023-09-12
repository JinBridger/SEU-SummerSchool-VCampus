package app.vcampus.client

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class NaviItem(
    val name: String,
    val path: String,
    val icon: ImageVector,
    val permission: List<String>
)

/**
 * navi items on nav rail
 * only users who have specific permission could see it
 */
val Navis = arrayListOf(
    NaviItem("主页", "/home", Icons.Default.Home, listOf("user")),
    NaviItem("学籍", "/student_status", Icons.Default.Person, listOf("student", "affairs_staff")),
    NaviItem("教务", "/teaching_affairs", Icons.Default.School, listOf("student", "teacher")),
    NaviItem("图书馆", "/library", Icons.Default.Book, listOf("library_user", "library_staff")),
    NaviItem("超市", "/shop", Icons.Default.LocalMall, listOf("shop_user", "shop_staff")),
    NaviItem("财务", "/finance", Icons.Default.Savings, listOf("finance_user", "finance_staff")),
    NaviItem("管理员", "/admin", Icons.Default.ManageAccounts, listOf("admin")),
    NaviItem("GPT", "/GPT", Icons.Default.Assistant, listOf("user")),
)