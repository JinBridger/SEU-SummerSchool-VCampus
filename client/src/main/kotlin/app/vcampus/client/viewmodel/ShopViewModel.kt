package app.vcampus.client.viewmodel


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.repository.copy
import app.vcampus.client.scene.components.SideBarItem
import app.vcampus.server.entity.StoreItem
import app.vcampus.server.entity.StoreTransaction
import app.vcampus.server.utility.Pair
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
    val selectItem = SelectItem(this)
    val myOrders = MyOrders()
    val dashboard = Dashboard()

    val searchStoreItem = SearchStoreItem()
    val modifyStoreItem = ModifyStoreItem()
    val addStoreItem = AddStoreItem()
    val searchTransaction = SearchStoreTransaction()

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


//    val chosenShopItems: List<_StoreItem> = _chosenShopItems

    private val _chosenItemsCount = mutableStateOf(0)
    val chosenItemsCount: MutableState<Int> = _chosenItemsCount

    private val _chosenItemsPrice = mutableStateOf(0)
    val chosenItemsPrice: MutableState<Int> = _chosenItemsPrice

//    var totalOrderItems = FakeRepository.getAllOrder()
//
//    fun manuallyUpdate() {
//        totalOrderItems = FakeRepository.getAllOrder()
//    }

    class SelectItem(private val parent: ShopViewModel) : ViewModel() {
        val totalShopItems = mutableStateListOf<StoreItem>()
        val chosenShopItems = mutableListOf<StoreItem>()
        val searchShopItems = mutableStateListOf<StoreItem>()

        val searched = mutableStateOf(false)

        fun getAllItems() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getAllItemsInternal().collect {
                        totalShopItems.clear()
                        totalShopItems.addAll(it)

                        chosenShopItems.clear()
                        chosenShopItems.addAll(totalShopItems.map { ti ->
                            ti.copy(stock = 0)
                        })
                    }
                }
            }
        }

        private suspend fun getAllItemsInternal() = flow {
            try {
                emit(FakeRepository.getAllStoreItems())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun checkout() {
            val items = chosenShopItems.map { Pair(it.uuid, it.stock) }.filter { it.second > 0 }

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    checkoutInternal(items).collect {
                        if (it) {
                            chosenShopItems.clear()
                            chosenShopItems.addAll(totalShopItems.map { ti ->
                                ti.copy(stock = 0)
                            })
                            parent.chosenItemsCount.value = 0
                            parent.chosenItemsPrice.value = 0
                        }
                    }
                }
            }
        }

        private suspend fun checkoutInternal(items: List<Pair<UUID, Int>>) = flow {
            try {
                emit(FakeRepository.buyItems(items))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        fun searchStoreItemSelect(keyword: String) {
            if (keyword == "") {
                searched.value = false
                return
            }

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    searchStoreItemInternal(keyword).collect {
                        searchShopItems.clear()
                        searchShopItems.addAll(it)

                        searched.value = true
                    }
                }
            }
        }

        private suspend fun searchStoreItemInternal(keyword: String) = flow {
            emit(FakeRepository.searchStoreItem(keyword))
        }
    }

    class MyOrders() : ViewModel() {
        var orders = mutableStateMapOf<String, List<StoreTransaction>>()

        fun getOrders() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getOrdersInternal().collect {
                        orders.clear()
                        orders.putAll(it)
                    }
                }
            }
        }

        private suspend fun getOrdersInternal() = flow {
            try {
                emit(FakeRepository.getAllOrder())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    open class SearchStoreTransaction : ViewModel() {
        val keyword = mutableStateOf("")
        val Transactions = mutableStateMapOf<String, List<StoreTransaction>>()
        val searched = mutableStateOf(false)

        fun searchStoreTransaction() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    searchStoreTransactionInternal().collect {
                        Transactions.clear()
                        Transactions.putAll(it)

                        searched.value = true
                    }
                }
            }
        }

        private suspend fun searchStoreTransactionInternal() = flow {
            emit(FakeRepository.searchTransaction(keyword.value))
        }
    }

    open class SearchStoreItem : ViewModel() {
        val storeList = mutableStateListOf<StoreItem>()
        var searched = mutableStateOf(false)

        fun searchStoreItem(keyword: String) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    searchStoreItemInternal(keyword).collect {
                        storeList.clear()
                        storeList.addAll(it)

                        searched.value = true
                    }
                }
            }
        }

        private suspend fun searchStoreItemInternal(keyword: String) = flow {
            emit(FakeRepository.searchStoreItem(keyword))
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
        var result = mutableStateOf(false)
        var showMessage = mutableStateOf(false)

        fun addStoreItem(newStoreItem: StoreItem) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    addStoreItemInternal(newStoreItem).collect {
                        result.value = it
                        showMessage.value = true
                    }
                }
            }
        }

        private suspend fun addStoreItemInternal(newItem: StoreItem) = flow {
            emit(FakeRepository.addStoreItem(newItem))
        }
    }

    class Dashboard : ViewModel() {
        var todaySales = mutableStateOf(0)

        fun getTodaySales() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getTodaySalesInternal().collect {
                        todaySales.value = it
                    }
                }
            }
        }

        private suspend fun getTodaySalesInternal() = flow {
            emit(FakeRepository.getTodaySalesVolume())
        }
    }

//    class CreateStoreItemTransaction : ViewModel(){
//        private suspend fun createStoreItemTransaction(newStoreTransaction: StoreTransaction) = flow {
//            emit(FakeRepository.createTransaction(StoreTransaction))
//        }
//    }
}