package app.vcampus.client.viewmodel


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateListOf
import app.vcampus.client.scene.components.SideBarItem
import moe.tlaster.precompose.viewmodel.ViewModel

class TeachingAffairsViewModel() : ViewModel() {
    val teachingAffairsSideBarItem = mutableStateListOf(
            SideBarItem(true, "教务信息", "", Icons.Default.Info, false),
            SideBarItem(false, "我的课表", "查看个人课表", Icons.Default.CalendarMonth, false),
            SideBarItem(false, "我的成绩", "查看个人成绩单", Icons.Default.FileOpen, false),

            SideBarItem(true, "教务工具", "", Icons.Default.Info, false),
            SideBarItem(false, "选课", "进入选课页面", Icons.Default.Checklist, false),
            SideBarItem(false, "评教", "进入评教页面", Icons.Default.Diversity1, false)
    )

}