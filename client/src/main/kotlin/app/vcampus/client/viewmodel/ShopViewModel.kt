package app.vcampus.client.viewmodel


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.repository.copy
import app.vcampus.client.scene.components.SideBarItem
import app.vcampus.server.entity.StoreItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import java.util.*


data class MutableStoreItem(
    var uuid: UUID = UUID.randomUUID(),
    var itemName: MutableState<String> = mutableStateOf(""),
    var price: MutableState<Int> = mutableStateOf(0),
    var pictureLink: MutableState<String> = mutableStateOf(""),
    var barcode: MutableState<String> = mutableStateOf(""),
    var stock: MutableState<Int> = mutableStateOf(0),
    var salesVolume: MutableState<Int> = mutableStateOf(0),
    var description: MutableState<String> = mutableStateOf("")
) {
    fun toStoreItem(): StoreItem {
        val newStoreItem = StoreItem()

        newStoreItem.uuid = uuid
        newStoreItem.itemName = itemName.value
        newStoreItem.price = price.value
        newStoreItem.pictureLink = pictureLink.value
        newStoreItem.barcode = barcode.value
        newStoreItem.stock = stock.value
        newStoreItem.salesVolume = salesVolume.value
        newStoreItem.description = description.value

        return newStoreItem
    }

    fun fromStoreItem(item: StoreItem) {
        uuid = item.uuid ?: UUID.randomUUID()
        itemName.value = item.itemName ?: ""
        price.value = item.price ?: 0
        pictureLink.value = item.pictureLink ?: ""
        barcode.value = item.barcode ?: ""
        stock.value = item.stock ?: 0
        salesVolume.value = item.salesVolume ?: 0
        description.value = item.description ?: ""
    }
}

class ShopViewModel() : ViewModel() {
    val identity = FakeRepository.user.roles.toList()
    val searchStoreItem = SearchStoreItem()
    val modifyStoreItem = ModifyStoreItem()
    val addStoreItem = AddStoreItem()

    val sideBarContent = (if (identity.contains("shop_user")) {
        listOf(SideBarItem(true, "购物", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "shop_user" -> listOf(
                SideBarItem(
                    false, "购物页面", "选择商品",
                    Icons.Default.ShoppingBasket, false
                )
            )

            else -> emptyList()
        }
    } + (if (identity.contains("shop_user")) {
        listOf(SideBarItem(true, "订单相关", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "shop_user" -> listOf(
                SideBarItem(
                    false, "我的订单", "查看所有订单",
                    Icons.Default.FormatListBulleted, false
                )
            )

            else -> emptyList()
        }
    } + (if (identity.contains("shop_staff")) {
        listOf(SideBarItem(true, "管理工具", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "shop_staff" -> listOf(
                SideBarItem(
                    false, "添加商品", "添加新的商品",
                    Icons.Default.LibraryAdd, false
                ),
                SideBarItem(
                    false, "修改商品", "修改商品信息",
                    Icons.Default.AutoFixHigh, false
                ),
                SideBarItem(
                    false, "查看后台信息", "仪表盘",
                    Icons.Default.Speed, false
                ),
            )

            else -> emptyList()
        }
    }

    val shopSideBarItem = sideBarContent.toMutableStateList()

    // SelectItemSubscene

    val totalShopItems = FakeRepository.getAllStoreItems()

    val oneNewShopItem = FakeRepository.getOneNewStoreItem()

    val chosenShopItems = totalShopItems.map {
        it.copy(stock = 0)
    }.toMutableList()

//    val chosenShopItems: List<_StoreItem> = _chosenShopItems

    private val _chosenItemsCount = mutableStateOf(0)
    val chosenItemsCount: MutableState<Int> = _chosenItemsCount

    private val _chosenItemsPrice = mutableStateOf(0)
    val chosenItemsPrice: MutableState<Int> = _chosenItemsPrice

    val totalOrderItems = FakeRepository.getAllOrder()

    open class SearchStoreItem : ViewModel() {
        val keyword = mutableStateOf("")
        val storeList = mutableStateMapOf<String, List<StoreItem>>()
        var searched = mutableStateOf(false)

        fun searchStoreItem() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    searchStoremItemInternal().collect {
                        storeList.clear()
                        storeList.putAll(it)

                        searched.value = true
                    }
                }
            }
        }

        private suspend fun searchStoremItemInternal() = flow {
            emit(FakeRepository.searchStoreItem(keyword.value))
        }
    }

    class ModifyStoreItem : SearchStoreItem() {
        fun updateStoreItem(storeItem: StoreItem) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    updateStoreItemInternal(storeItem).collect {
                    }
                }
            }
        }

        private suspend fun updateStoreItemInternal(storeItem: StoreItem) = flow {
            try {
                emit(FakeRepository.updateStoreItem(storeItem))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class AddStoreItem : ViewModel() {
        var result = mutableStateOf(true)
        var showMessage = mutableStateOf(false)

        fun addStoreItem(newStoreItem: StoreItem) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    addStoreItemInternal(newStoreItem).collect {
                        result.value = it
                        showMessage.value = it
                    }
                }
            }
        }

        private suspend fun addStoreItemInternal(newItem: StoreItem) = flow {
            emit(FakeRepository.addStoreItem(newItem))
        }
    }
}