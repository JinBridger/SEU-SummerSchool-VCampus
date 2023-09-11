package app.vcampus.client.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.SideBarItem
import app.vcampus.server.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class AdminViewModel: ViewModel() {
    val identity = FakeRepository.user.roles.toList()

    val modifyUser = ModifyUser()

    val sideBarContent = (if (identity.contains("admin")) {
        listOf(SideBarItem(true, "管理员", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "admin" -> listOf(
                    SideBarItem(false, "添加账户", "添加登录账户",
                            Icons.Default.Info, false),
                    SideBarItem(false, "修改账户", "修改账户权限 / 密码",
                            Icons.Default.Info, false))
            else -> emptyList()
        }
    }

    val adminSideBarItem = sideBarContent.toMutableStateList()

    class ModifyUser: ViewModel() {
        val keyword = mutableStateOf("")
        val userList = mutableStateListOf<User>()

        private var inited = false

        fun init() {
            if (inited) return
            inited = true
            searchUser()
        }

        fun searchUser() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    searchUserInternal().collect {
                        userList.clear()
                        userList.addAll(it)
                    }
                }
            }
        }

        private suspend fun searchUserInternal() = flow {
            try {
                emit(FakeRepository.searchUser(keyword.value))
            } catch (e: Exception) {
                emit(emptyList())
            }
        }

        fun modifyUser(cardNum: Int, password: String, roles: List<String>) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    modifyUserInternal(cardNum, password, roles).collect {

                    }
                }
            }
        }

        private suspend fun modifyUserInternal(cardNum: Int, password: String, roles: List<String>) = flow {
            try {
                emit(FakeRepository.modifyUser(cardNum, password, roles))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}