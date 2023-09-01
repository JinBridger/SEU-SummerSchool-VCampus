package app.vcampus.client.viewmodel


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.SideBarItem
import moe.tlaster.precompose.viewmodel.ViewModel

class TeachingAffairsViewModel() : ViewModel() {
    val identity = FakeRepository.user.roles.toList()

    val sideBarContent = (if (identity.contains("student") || identity.contains(
                    "teacher")) {
        listOf(SideBarItem(true, "教务信息", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "student" -> listOf(
                    SideBarItem(false, "我的课表", "查看个人课表",
                            Icons.Default.CalendarMonth, false),
                    SideBarItem(false, "我的成绩", "查看个人成绩单",
                            Icons.Default.FileOpen, false)
            )

            "teacher" -> listOf(
                    SideBarItem(false, "我的课堂", "查看教学班信息",
                            Icons.Default.CalendarMonth, false),
                    SideBarItem(false, "评教结果", "查看评教结果",
                            Icons.Default.Money, false)
            )

            else -> emptyList()
        }
    } + (if (identity.contains("student") || identity.contains(
                    "affairs_staff")) {
        listOf(SideBarItem(true, "教务工具", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "student" -> listOf(SideBarItem(false, "选课", "进入选课页面",
                    Icons.Default.Checklist, false),
                    SideBarItem(false, "评教", "进入评教页面",
                            Icons.Default.Diversity1, false))

            "affairs_staff" -> listOf(
                    SideBarItem(false, "排课", "进行排课",
                            Icons.Default.EditCalendar, false),
                    SideBarItem(false, "录入成绩", "录入课程成绩",
                            Icons.Default.HistoryEdu, false)
            )

            else -> emptyList()
        }
    }

    val teachingAffairsSideBarItem = sideBarContent.toMutableStateList()

    // class table
    val schedules = FakeRepository.getAllSchedule()
}