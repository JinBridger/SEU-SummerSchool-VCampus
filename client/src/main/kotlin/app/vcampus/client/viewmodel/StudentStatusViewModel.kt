package app.vcampus.client.viewmodel

import androidx.compose.runtime.mutableStateOf
import app.vcampus.client.repository.FakeRepository
import app.vcampus.server.entity.Student
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

    init {
        getStudentStatus()
    }

    fun getStudentStatus() {
        viewModelScope.launch {
            getStudentStatusInternal().collect {
                student.value = it

                cardNumber.value = it.cardNumber.toString()
                studentNumber.value = it.studentNumber.toString()
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