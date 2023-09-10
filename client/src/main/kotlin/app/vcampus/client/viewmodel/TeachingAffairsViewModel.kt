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
import java.util.*

class TeachingAffairsViewModel() : ViewModel() {
    val identity = FakeRepository.user.roles.toList()
    val myClasses = MyClasses(identity.contains("student"))
    val myTeachingClasses = MyTeachingClasses(identity.contains("teacher"))
    val chooseClass = ChooseClass(identity.contains("student"))
//    val evaluateTeacher = EvaluateTeacher(identity.contains("student"))
//    val evaluateResult = EvaluateResult(identity.contains("teacher"))

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
            "affairs_staff"
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

            "affairs_staff" -> listOf(
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

    class MyClasses(init: Boolean) : ViewModel() {
        val selected = mutableStateListOf<TeachingClass>()

        init {
            if (init) getSelectedClasses()
        }

        private fun getSelectedClasses() {
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
    }

    class MyTeachingClasses(init: Boolean) : ViewModel() {
        val myClasses = mutableListOf<TeachingClass>()

        init {
            if (init) getMyTeachingClasses()
        }

        private fun getMyTeachingClasses() {
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
    }

    class ChooseClass(init: Boolean) : ViewModel() {
        val allCourses = mutableStateListOf<Course>()

        init {
            if (init) {
                getSelectableCourses()
            }
        }

//        private fun getAllCourses() {
//            val tmpClass1 = TeachingClass()
//            tmpClass1.uuid = UUID.fromString("1df17ad4-7a9c-4f14-83d6-28512aee2b33")
//            tmpClass1.capacity = 100
//            tmpClass1.courseName = "软件工程"
//            tmpClass1.courseUuid = UUID.fromString("e1386a64-dd0d-4422-967b-fdeadee68e30")
//            tmpClass1.place = "东九楼"
//            tmpClass1.schedule = listOf(Pair(Pair(1, 16), Pair(1, Pair(1, 2))))
//            tmpClass1.teacherId = 123456
//            tmpClass1.teacherName = "admin"
//
//            val tmpClass2 = TeachingClass()
//            tmpClass2.uuid = UUID.fromString("353899bd-ffc4-4bc5-b152-539bb87ff728")
//            tmpClass2.capacity = 100
//            tmpClass2.courseName = "软件工程"
//            tmpClass2.courseUuid = UUID.fromString("e1386a64-dd0d-4422-967b-fdeadee68e30")
//            tmpClass2.place = "东九楼"
//            tmpClass2.schedule = listOf(Pair(Pair(1, 16), Pair(1, Pair(1, 2))))
//            tmpClass2.teacherId = 123456
//            tmpClass2.teacherName = "admin"
//
//            val tmpClass3 = TeachingClass()
//            tmpClass3.uuid = UUID.fromString("c30d2d57-8edc-4471-b143-4d2df741b6e8")
//            tmpClass3.capacity = 100
//            tmpClass3.courseName = "软件工程"
//            tmpClass3.courseUuid = UUID.fromString("e1386a64-dd0d-4422-967b-fdeadee68e30")
//            tmpClass3.place = "东九楼"
//            tmpClass3.schedule = listOf(Pair(Pair(1, 16), Pair(1, Pair(1, 2))))
//            tmpClass3.teacherId = 123456
//            tmpClass3.teacherName = "admin"
//
//            val tmpCourse = Course()
//            tmpCourse.courseId = "BJSL0081"
//            tmpCourse.uuid = UUID.fromString("e1386a64-dd0d-4422-967b-fdeadee68e30")
//            tmpCourse.courseName = "软件工程"
//            tmpCourse.credit = 4F
//            tmpCourse.school = "计算机科学与工程学院"
//            tmpCourse.teachingClasses = listOf(
//                tmpClass1, tmpClass2, tmpClass3
//            )
//            allCourses.add(tmpCourse)
//        }

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

        }

        fun returnClass(teachingClassUuid: UUID) {

        }
    }

//    class EvaluateTeacher(init: Boolean): ViewModel() {
//        val unevaluatedClasses = mutableListOf<TeachingClass>()
//
//        init {
//            if(init) {
//                getUnevaluatedClasses()
//            }
//        }
//
//        private fun getUnevaluatedClasses() {
//            val tmpClass1 = TeachingClass()
//            tmpClass1.uuid = UUID.fromString("1df17ad4-7a9c-4f14-83d6-28512aee2b33")
//            tmpClass1.capacity = 100
//            tmpClass1.courseName = "软件工程"
//            tmpClass1.courseUuid = UUID.fromString("e1386a64-dd0d-4422-967b-fdeadee68e30")
//            tmpClass1.place = "东九楼"
//            tmpClass1.schedule = listOf(Pair(Pair(1, 16), Pair(1, Pair(1, 2))))
//            tmpClass1.teacherId = 123456
//            tmpClass1.teacherName = "admin"
//
//            val tmpClass2 = TeachingClass()
//            tmpClass2.uuid = UUID.fromString("353899bd-ffc4-4bc5-b152-539bb87ff728")
//            tmpClass2.capacity = 100
//            tmpClass2.courseName = "软件工程"
//            tmpClass2.courseUuid = UUID.fromString("e1386a64-dd0d-4422-967b-fdeadee68e30")
//            tmpClass2.place = "东九楼"
//            tmpClass2.schedule = listOf(Pair(Pair(1, 16), Pair(1, Pair(1, 2))))
//            tmpClass2.teacherId = 123456
//            tmpClass2.teacherName = "admin"
//
//            val tmpClass3 = TeachingClass()
//            tmpClass3.uuid = UUID.fromString("c30d2d57-8edc-4471-b143-4d2df741b6e8")
//            tmpClass3.capacity = 100
//            tmpClass3.courseName = "软件工程"
//            tmpClass3.courseUuid = UUID.fromString("e1386a64-dd0d-4422-967b-fdeadee68e30")
//            tmpClass3.place = "东九楼"
//            tmpClass3.schedule = listOf(Pair(Pair(1, 16), Pair(1, Pair(1, 2))))
//            tmpClass3.teacherId = 123456
//            tmpClass3.teacherName = "admin"
//
//            val tmpCourse = Course()
//            tmpCourse.courseId = "BJSL0081"
//            tmpCourse.uuid = UUID.fromString("e1386a64-dd0d-4422-967b-fdeadee68e30")
//            tmpCourse.courseName = "软件工程"
//            tmpCourse.credit = 4F
//            tmpCourse.school = "计算机科学与工程学院"
//            tmpCourse.teachingClasses = listOf(
//                    tmpClass1, tmpClass2, tmpClass3
//            )
//
//            tmpClass1.course = tmpCourse
//            tmpClass2.course = tmpCourse
//            tmpClass3.course = tmpCourse
//
//            unevaluatedClasses.add(tmpClass1)
//            unevaluatedClasses.add(tmpClass2)
//            unevaluatedClasses.add(tmpClass3)
//        }
//
//        fun sendEvaluationResult(result: Pair<UUID, Pair<List<Int>, String>>) {
//            println(result)
//        }
//    }
}