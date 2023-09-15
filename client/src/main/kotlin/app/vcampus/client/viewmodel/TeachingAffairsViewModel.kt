package app.vcampus.client.viewmodel


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.SideBarItem
import app.vcampus.server.entity.Course
import app.vcampus.server.entity.TeachingClass
import app.vcampus.server.utility.Pair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import java.io.File
import java.util.*

class TeachingAffairsViewModel() : ViewModel() {
    val identity = FakeRepository.user.roles.toList()
    val myClasses = MyClasses()
    val myTeachingClasses = MyTeachingClasses()

    val sideBarContent = (if (identity.contains("student") || identity.contains(
            "teacher"
        )
    ) {
        listOf(SideBarItem(true, "教务信息", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "student" -> listOf(
                SideBarItem(
                    false, "我的课表", "查看个人课表",
                    Icons.Default.CalendarMonth, false
                ),
                SideBarItem(
                    false, "我的成绩", "查看个人成绩单",
                    Icons.Default.FileOpen, false
                )
            )

            "teacher" -> listOf(
                SideBarItem(
                    false, "我的课堂", "查看教学班信息",
                    Icons.Default.CalendarMonth, false
                ),
                SideBarItem(
                    false, "评教结果", "查看评教结果",
                    Icons.Default.Money, false
                )
            )

            else -> emptyList()
        }
    } + (if (identity.contains("student") || identity.contains(
            "teacher"
        )
    ) {
        listOf(SideBarItem(true, "教务工具", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "student" -> listOf(
                SideBarItem(
                    false, "选课", "进入选课页面",
                    Icons.Default.Checklist, false
                ),
                SideBarItem(
                    false, "评教", "进入评教页面",
                    Icons.Default.Diversity1, false
                )
            )

            "teacher" -> listOf(
//                SideBarItem(
//                    false, "排课", "进行排课",
//                    Icons.Default.EditCalendar, false
//                ),
                SideBarItem(
                    false, "录入成绩", "录入课程成绩",
                    Icons.Default.HistoryEdu, false
                )
            )

            else -> emptyList()
        }
    }

    val teachingAffairsSideBarItem = sideBarContent.toMutableStateList()

    // class table
//    val schedules = FakeRepository.getFakeSchedule()

//    val StudentGradeItems = FakeRepository.getStudentGrade()

    class MyClasses() : ViewModel() {
        val selected = mutableStateListOf<TeachingClass>()
        val allCourses = mutableStateListOf<Course>()

        private var inited = false

        fun init() {
            if (inited) return
            inited = true
            getSelectedClasses()
            getSelectableCourses()
        }

        fun getSelectedClasses() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getSelectedClassesInternal().collect {
                        selected.clear()
                        selected.addAll(it)
                    }
                }
            }
        }

        private suspend fun getSelectedClassesInternal() = flow {
            try {
                emit(FakeRepository.getSelectedClasses())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun sendEvaluationResult(result: Pair<UUID, Pair<List<Int>, String>>) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    sendEvaluationResultInternal(result).collect {
                        getSelectedClasses()
                    }
                }
            }
        }

        private suspend fun sendEvaluationResultInternal(result: Pair<UUID, Pair<List<Int>, String>>) = flow {
            try {
                emit(FakeRepository.sendEvaluationResult(result))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun getSelectableCourses() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getSelectableCoursesInternal().collect {
                        allCourses.clear()
                        allCourses.addAll(it)
                    }
                }
            }
        }

        private suspend fun getSelectableCoursesInternal() = flow {
            try {
                emit(FakeRepository.getSelectableCourses())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun chooseClass(teachingClassUuid: UUID) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    chooseClassInternal(teachingClassUuid).collect {
                        getSelectableCourses()
                        getSelectedClasses()
                    }
                }
            }
        }

        private suspend fun chooseClassInternal(teachingClassUuid: UUID) = flow {
            try {
                emit(FakeRepository.chooseClass(teachingClassUuid))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun dropClass(teachingClassUuid: UUID) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    dropClassInternal(teachingClassUuid).collect {
                        getSelectableCourses()
                        getSelectedClasses()
                    }
                }
            }
        }

        private suspend fun dropClassInternal(teachingClassUuid: UUID) = flow {
            try {
                emit(FakeRepository.dropClass(teachingClassUuid))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class MyTeachingClasses() : ViewModel() {
        val myClasses = mutableStateListOf<TeachingClass>()

        private var inited = false

        fun init() {
            if (inited) return
            inited = true
            getMyTeachingClasses()
        }

        fun getMyTeachingClasses() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getMyTeachingClassesInternal().collect {
                        myClasses.clear()
                        myClasses.addAll(it)
                    }
                }
            }
        }

        private suspend fun getMyTeachingClassesInternal() = flow {
            try {
                emit(FakeRepository.getMyTeachingClasses())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun saveStudentList(teachingClass: TeachingClass, file: File) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    saveStudentListInternal(teachingClass).collect {
                        file.writeBytes(Base64.getDecoder().decode(it))
                    }
                }
            }
        }

        private suspend fun saveStudentListInternal(teachingClass: TeachingClass) = flow {
            try {
                emit(FakeRepository.exportStudentList(teachingClass))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun gradeTemplate(teachingClass: TeachingClass, file: File) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    gradeTemplateInternal(teachingClass).collect {
                        file.writeBytes(Base64.getDecoder().decode(it))
                    }
                }
            }
        }

        private suspend fun gradeTemplateInternal(teachingClass: TeachingClass) = flow {
            try {
                emit(FakeRepository.exportGradeTemplate(teachingClass))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun importGrade(teachingClass: TeachingClass, file: File) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    importGradeInternal(teachingClass, Base64.getEncoder().encodeToString(file.readBytes())).collect {

                    }
                }
            }
        }

        private suspend fun importGradeInternal(teachingClass: TeachingClass, file: String) = flow {
            try {
                emit(FakeRepository.importGrade(teachingClass, file))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}