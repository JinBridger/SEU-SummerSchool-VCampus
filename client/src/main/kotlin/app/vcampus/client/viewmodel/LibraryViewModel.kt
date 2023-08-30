package app.vcampus.client.viewmodel


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateListOf
import app.vcampus.client.scene.components.SideBarItem
import moe.tlaster.precompose.viewmodel.ViewModel

class LibraryViewModel : ViewModel() {
    val librarySideBarItem = mutableStateListOf(
            SideBarItem(true, "查询", "", Icons.Default.Info, false),
            SideBarItem(false, "查询图书", "查找图书馆藏书", Icons.Default.Search, false),
            SideBarItem(false, "我的书籍", "查看已借阅书籍", Icons.Default.MenuBook, false),

            SideBarItem(true, "借还相关", "", Icons.Default.Info, false),
            SideBarItem(false, "预约还书", "预约还书时间", Icons.Default.Event, false),
    )
}