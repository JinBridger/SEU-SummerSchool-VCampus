package app.vcampus.client.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.SideBarItem
import app.vcampus.server.entity.Student
import app.vcampus.server.enums.Gender
import app.vcampus.server.enums.PoliticalStatus
import app.vcampus.server.enums.StudentStatus
import app.vcampus.server.utility.DateUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class MutableStudent(
    val cardNumber: MutableState<Int> = mutableStateOf(0),
    val studentNumber: MutableState<String> = mutableStateOf(""),
    val familyName: MutableState<String> = mutableStateOf(""),
    val givenName: MutableState<String> = mutableStateOf(""),
    val gender: MutableState<Gender> = mutableStateOf(Gender.unspecified),
    val birthDate: MutableState<String> = mutableStateOf(""),
    val major: MutableState<String> = mutableStateOf(""),
    val school: MutableState<String> = mutableStateOf(""),
    val birthPlace: MutableState<String> = mutableStateOf(""),
    val politicalStatus: MutableState<PoliticalStatus> = mutableStateOf(PoliticalStatus.Masses),
    val status: MutableState<StudentStatus> = mutableStateOf(StudentStatus.inSchool)
) {
    fun toStudent(): Student {
        val newStudent = Student()

        newStudent.cardNumber = cardNumber.value
        newStudent.studentNumber = studentNumber.value
        newStudent.familyName = familyName.value
        newStudent.givenName = givenName.value
        newStudent.gender = gender.value
        newStudent.birthDate = DateUtility.toDate(birthDate.value)
        newStudent.major = major.value
        newStudent.school = school.value
        newStudent.birthPlace = birthPlace.value
        newStudent.politicalStatus = politicalStatus.value
        newStudent.status = status.value

        return newStudent
    }

    fun fromStudent(student: Student) {
        cardNumber.value = student.cardNumber ?: 0
        studentNumber.value = student.studentNumber ?: ""
        familyName.value = student.familyName ?: ""
        givenName.value = student.givenName ?: ""
        gender.value = student.gender ?: Gender.unspecified
        birthDate.value = DateUtility.fromDate(student.birthDate) ?: ""
        major.value = student.major ?: ""
        school.value = student.school ?: ""
        birthPlace.value = student.birthPlace ?: ""
        politicalStatus.value = student.politicalStatus ?: PoliticalStatus.Masses
        status.value = student.status ?: StudentStatus.inSchool
    }
}

class StudentStatusViewModel() : ViewModel() {
    val identity = FakeRepository.user.roles.toList()
    val student = mutableStateOf(MutableStudent())

    val searchKeyword = mutableStateOf("")
    val searchedStudents = mutableStateListOf<Student>()

    val sideBarContent = (if (identity.contains("student") || identity.contains(
            "teacher"
        )
    ) {
        listOf(SideBarItem(true, "学籍信息", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "student" -> listOf(
                SideBarItem(
                    false, "我的学籍信息", "查看我的学籍信息",
                    Icons.Default.Info, false
                )
            )

            "affairs_staff" -> listOf(
                SideBarItem(
                    false, "修改学籍信息", "修改学籍信息",
                    Icons.Default.Info, false
                )
            )

            else -> emptyList()
        }
    }

    val studentStatusSideBarItem = sideBarContent.toMutableStateList()

    fun getStudentStatus() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getStudentStatusInternal().collect {
                    student.value.fromStudent(it)
                }
            }
        }
    }

    private suspend fun getStudentStatusInternal() = flow {
        try {
            emit(FakeRepository.getSelf())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun searchStudent() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                searchStudentInternal().collect {
                    searchedStudents.clear()
                    searchedStudents.addAll(it)
                }
            }
        }
    }

    private suspend fun searchStudentInternal() = flow {
        try {
            emit(FakeRepository.searchStudent(searchKeyword.value))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateStudentInternal(student).collect {

                }
            }
        }
    }

    private suspend fun updateStudentInternal(student: Student) = flow {
        try {
            emit(FakeRepository.updateStudent(student))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}