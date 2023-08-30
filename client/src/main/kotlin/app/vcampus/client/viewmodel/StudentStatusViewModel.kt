package app.vcampus.client.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.SideBarItem
import app.vcampus.server.entity.Student
import app.vcampus.server.utility.DateUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import java.util.*

class StudentStatusViewModel() : ViewModel() {
    val familyName = mutableStateOf("")
    val givenName = mutableStateOf("")
    val gender = mutableStateOf("")
    val birthDate = mutableStateOf("")
    val major = mutableStateOf("")
    val school = mutableStateOf("")
    val cardNumber = mutableStateOf("")
    val studentNumber = mutableStateOf("")
    val birthPlace = mutableStateOf("")
    val politicalStatus = mutableStateOf("")
    val status = mutableStateOf("")

    val student = mutableStateOf(Student())

    val studentStatusSideBarItem = mutableStateListOf(SideBarItem(true, "学籍信息", "", Icons.Default.Info, false), SideBarItem(false, "我的学籍信息", "查看我的学籍信息", Icons.Default.Info, false))

    init {
        getStudentStatus()
    }

    fun getStudentStatus() {
        viewModelScope.launch {
            getStudentStatusInternal().collect {
                student.value = it

                cardNumber.value = it.cardNumber.toString()
                studentNumber.value = it.studentNumber
                familyName.value = it.familyName
                givenName.value = it.givenName
                gender.value = it.getGender().label
                birthDate.value = DateUtility.fromDate(it.birthDate)
                major.value = it.major.toString()
                school.value = it.school.toString()
                birthPlace.value = it.birthPlace
                politicalStatus.value = it.politicalStatus.label
                status.value = it.status.label
            }
        }
    }

    suspend fun getStudentStatusInternal() = flow {
        try {
            emit(FakeRepository.getSelf())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}