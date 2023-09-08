package app.vcampus.client.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.runtime.toMutableStateList
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.SideBarItem
import moe.tlaster.precompose.viewmodel.ViewModel

class AdminViewModel: ViewModel() {
    val identity = FakeRepository.user.roles.toList()

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
}