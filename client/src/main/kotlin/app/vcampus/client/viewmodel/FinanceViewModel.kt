package app.vcampus.client.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.runtime.toMutableStateList
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.SideBarItem
import moe.tlaster.precompose.viewmodel.ViewModel

class FinanceViewModel() : ViewModel() {
    val identity = FakeRepository.user.roles.toList()
    val sideBarContent = (if (identity.contains("finance_user")) {
        listOf(SideBarItem(true, "余额相关", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "finance_user" -> listOf(
                    SideBarItem(false, "一卡通", "查看一卡通余额与账单",
                            Icons.Default.CreditCard, false),
                    SideBarItem(false, "自助充值", "自助充值校园卡",
                            Icons.Default.AddCard, false))
            else -> emptyList()
        }
    } + (if (identity.contains("finance_staff")) {
        listOf(SideBarItem(true, "管理工具", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "finance_staff" -> listOf(
                    SideBarItem(false, "充值", "手动修改一卡通余额",
                            Icons.Default.RequestQuote, false))
            else -> emptyList()
        }
    }

    val financeSideBarItem = sideBarContent.toMutableStateList()
}