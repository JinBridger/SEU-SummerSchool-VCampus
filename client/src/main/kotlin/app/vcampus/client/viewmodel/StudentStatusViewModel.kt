package app.vcampus.client.viewmodel

import androidx.compose.runtime.mutableStateOf
import moe.tlaster.precompose.viewmodel.ViewModel
import java.util.*

class StudentStatusViewModel(): ViewModel() {
    val familyName = mutableStateOf("")
    val givenName = mutableStateOf("")
    val gender = mutableStateOf("")
    val birthDate = mutableStateOf(Date())
    val major = mutableStateOf("")
    val school = mutableStateOf("")
    val cardNumber = mutableStateOf("")
    val studentNumber = mutableStateOf("")
}