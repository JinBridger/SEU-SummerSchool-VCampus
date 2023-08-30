package app.vcampus.client.viewmodel


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.SideBarItem
import moe.tlaster.precompose.viewmodel.ViewModel

class LibraryViewModel : ViewModel() {
    val identity = FakeRepository.user.roles.toList()

    val sideBarContent = (if (identity.contains("library_user")) {
        listOf(SideBarItem(true, "查询", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "library_user" -> listOf(
                    SideBarItem(false, "查询图书", "查找图书馆藏书",
                            Icons.Default.Search, false),
                    SideBarItem(false, "我的书籍", "查看已借阅书籍",
                            Icons.Default.MenuBook, false)
            )

            else -> emptyList()
        }
    } + (if (identity.contains("library_user")) {
        listOf(SideBarItem(true, "借还相关", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "library_user" -> listOf(
                    SideBarItem(false, "预约还书", "预约还书时间",
                            Icons.Default.Event,
                            false)
            )

            else -> emptyList()
        }
    } + (if (identity.contains("library_staff")) {
        listOf(SideBarItem(true, "管理工具", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "library_staff" -> listOf(
                    SideBarItem(false, "添加图书", "添加新的图书",
                            Icons.Default.BookmarkAdd,
                            false),
                    SideBarItem(false, "修改图书", "修改现有图书",
                            Icons.Default.AutoFixHigh,
                            false),
                    SideBarItem(false, "办理借还书", "办理借还书业务",
                            Icons.Default.Queue,
                            false)
            )

            else -> emptyList()
        }
    }

    val librarySideBarItem = sideBarContent.toMutableStateList()
}