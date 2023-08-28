package app.vcampus.client.viewmodel

import androidx.compose.runtime.mutableStateOf
import app.vcampus.client.repository.FakeRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class LoginViewModel() : ViewModel() {
    val loginState = mutableStateOf(false)

    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginInternal(username, password).collect {
                loginState.value = it
            }
        }
    }

    private suspend fun loginInternal(username: String, password: String) = flow {
        try {
            emit(FakeRepository.login(username, password))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}