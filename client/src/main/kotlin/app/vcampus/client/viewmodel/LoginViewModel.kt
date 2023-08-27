package app.vcampus.client.viewmodel

import app.vcampus.client.repository.FakeRepository
import moe.tlaster.precompose.viewmodel.ViewModel

class LoginViewModel() : ViewModel() {
    fun login(username: String, password: String): Boolean {
        return FakeRepository.login(username, password)
    }
}