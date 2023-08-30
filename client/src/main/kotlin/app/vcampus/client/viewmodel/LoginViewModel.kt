package app.vcampus.client.viewmodel

import androidx.compose.runtime.mutableStateOf
import app.vcampus.client.repository.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class LoginViewModel : ViewModel() {
    val loginState = mutableStateOf(false)

    fun login(username: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                loginState.value = FakeRepository.login(username, password)
            }
        }
    }
}