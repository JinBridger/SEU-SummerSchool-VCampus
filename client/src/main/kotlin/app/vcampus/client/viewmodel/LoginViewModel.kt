package app.vcampus.client.viewmodel

import androidx.compose.runtime.mutableStateOf
import app.vcampus.client.Application
import app.vcampus.client.repository.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class LoginViewModel : ViewModel() {
    val loginState = mutableStateOf(false)
    val showMessage = mutableStateOf(false)

    val server = mutableStateOf("127.0.0.1:9091")

    fun login(username: String, password: String) {
        if (!FakeRepository.isConnected) {
            try {
                val handler = Application.connect(server.value.split(":")[0], server.value.split(":")[1].toInt())
                FakeRepository.handler = handler
                FakeRepository.isConnected = true
            } catch (e: Exception) {
                showMessage.value = true
                return
            }
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = FakeRepository.login(username, password)
                if (result) {
                    loginState.value = true
                } else {
                    showMessage.value = true
                }
            }
        }
    }
}