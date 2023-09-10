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
import app.vcampus.server.entity.CardTransaction
import app.vcampus.server.entity.FinanceCard
import app.vcampus.server.enums.CardStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class FinanceViewModel() : ViewModel() {
    val identity = FakeRepository.user.roles.toList()

    val mybills = MyBillsViewModel()
    val staff = StaffViewModel()

    val sideBarContent = (if (identity.contains("finance_user")) {
        listOf(SideBarItem(true, "余额相关", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "finance_user" -> listOf(
                SideBarItem(
                    false, "一卡通", "查看一卡通余额与账单",
                    Icons.Default.CreditCard, false
                )
            )

            else -> emptyList()
        }
    } + (if (identity.contains("finance_staff")) {
        listOf(SideBarItem(true, "管理工具", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "finance_staff" -> listOf(
                SideBarItem(
                    false, "一卡通管理", "充值 / 冻结 / 挂失",
                    Icons.Default.RequestQuote, false
                )
            )

            else -> emptyList()
        }
    }

    val financeSideBarItem = sideBarContent.toMutableStateList()

    class MyBillsViewModel() : ViewModel() {
        val myCard = mutableStateOf(FinanceCard())
        val myBills = mutableStateListOf<CardTransaction>()

        private var inited = false

        fun init() {
            if (inited) return
            inited = true
            getMyCard()
            getMyBills()
        }

        private fun getMyCard() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getMyCardInternal().collect {
                        myCard.value = it
                    }
                }
            }
        }

        private suspend fun getMyCardInternal() = flow {
            try {
                emit(FakeRepository.getMyCard())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun getMyBills() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getMyBillsInternal().collect {
                        myBills.clear()
                        myBills.addAll(it)
                    }
                }
            }
        }

        private suspend fun getMyBillsInternal() = flow {
            try {
                emit(FakeRepository.getMyBills())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class StaffViewModel() : ViewModel() {
        val cardNumber = mutableStateOf("")
        val searchedCard = mutableStateOf(FinanceCard())
        val rechargeAmount = mutableStateOf("")

        fun getByCardNumber() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getByCardNumberInternal().collect {
                        searchedCard.value = it
                    }
                }
            }
        }

        private suspend fun getByCardNumberInternal() = flow {
            try {
                emit(FakeRepository.getByCardNumber(cardNumber.value))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun freezeCard() {
            searchedCard.value.status = CardStatus.frozen
            updateCard()
        }

        fun reportLoss() {
            searchedCard.value.status = CardStatus.lost
            updateCard()
        }

        fun unfreezeCard() {
            searchedCard.value.status = CardStatus.normal
            updateCard()
        }

        private fun updateCard() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    updateCardInternal().collect {
                        searchedCard.value = FinanceCard()
                        searchedCard.value = it
                    }
                }
            }
        }

        private suspend fun updateCardInternal() = flow {
            try {
                emit(FakeRepository.updateCard(searchedCard.value))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun rechargeCard() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    rechargeCardInternal().collect {
                        searchedCard.value = FinanceCard()
                        searchedCard.value = it
                    }
                }
            }
        }

        private suspend fun rechargeCardInternal() = flow {
            try {
                emit(
                    FakeRepository.rechargeCard(
                        searchedCard.value.cardNumber,
                        (rechargeAmount.value.toDouble() * 100).toInt()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}