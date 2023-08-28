package app.vcampus.client.viewmodel

import androidx.compose.runtime.mutableStateOf
import app.vcampus.client.repository.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import java.util.*

class StudentStatusViewModel() : ViewModel() {
    val familyName = mutableStateOf("")
    val givenName = mutableStateOf("")
    val gender = mutableStateOf("")
    val birthDate = mutableStateOf(Date())
    val major = mutableStateOf("")
    val school = mutableStateOf("")
    val cardNumber = mutableStateOf("")
    val studentNumber = mutableStateOf("")

    init {
        getStudentStatus()
    }

    fun getStudentStatus() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val student = FakeRepository.getSelf()
                println("wtfisthis: ")
                println(student.toString())
                cardNumber.value = student.getCardNumber().toString()
                studentNumber.value = student.getStudentNumber().toString()
            }
        }
    }
}