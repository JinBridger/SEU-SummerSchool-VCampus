package app.vcampus.client.repository

import app.vcampus.client.gateway.AuthClient
import app.vcampus.client.gateway.LibraryClient
import app.vcampus.client.gateway.StoreClient
import app.vcampus.client.gateway.StudentStatusClient
import app.vcampus.client.net.NettyHandler
import app.vcampus.server.entity.LibraryBook
import app.vcampus.server.entity.StoreItem
import app.vcampus.server.entity.Student
import app.vcampus.server.entity.User
import app.vcampus.server.utility.Pair
import moe.tlaster.precompose.viewmodel.ViewModel
import mu.KotlinLogging
import java.util.UUID


data class _GradeItem(
    val courseName: String, val grade: Int,
    val credit: Double, val courseId: String)


data class _StoreItem(val itemName: String, val price: Int,
                      val barcode: String, val stock: Int)

data class _StoreOrder(val date: String, val order: List<_StoreItem>)

data class _TeachingClass(
        val schedule: List<Pair<Pair<Int, Int>, Pair<Int, Pair<Int, Int>>>>,
        val courseName: String, val teacherId: Int, val position: String)

fun StoreItem.copy(stock: Int = this.stock): StoreItem {
    val copied = StoreItem() // Assuming StoreItem has a constructor that takes a name.
    copied.stock = stock
    copied.uuid = uuid
    copied.pictureLink = pictureLink
    copied.itemName = itemName
    copied.salesVolume = salesVolume
    copied.price = price
    copied.barcode = barcode
    copied.description = description
    return copied
}

object FakeRepository {
    private lateinit var handler: NettyHandler;
    lateinit var user: User;
    private val logger = KotlinLogging.logger {}

    fun setHandler(handler: NettyHandler) {
        this.handler = handler
    }

    fun login(username: String, password: String): Boolean {
        val user = AuthClient.login(handler, username, password)

//        val tempStoreItem = StoreItem()
//        tempStoreItem.description = "test"
//        tempStoreItem.itemName = "任天堂Switch OLED日版主机NS续航港版健身朱紫塞尔达限定游戏机"
//        tempStoreItem.barcode = "0123456789123"
//        tempStoreItem.price = 279900
//        tempStoreItem.stock = 99
//        tempStoreItem.salesVolume = 1
//        tempStoreItem.pictureLink = "https://i.dawnlab.me/05f0f5392e8efc95de553bafa2e30722.png"
//        StoreClient.addItem(handler, tempStoreItem)

        user?.let {
            logger.debug { it }
            this.user = it
            return true
        }

        return false
    }

    fun getSelf(): Student {
        return StudentStatusClient.getSelf(handler)
    }

    fun preAddBook(isbn: String): LibraryBook {
        return LibraryClient.preAddBook(handler, isbn) ?: LibraryBook()
    }

    fun addBook(newBook: LibraryBook): Boolean {
        return LibraryClient.addBook(handler, newBook)
    }

    fun searchBook(keyword: String): Map<String, List<LibraryBook>> {
        return LibraryClient.searchBook(handler, keyword) ?: mapOf()
    }

    fun updateBook(book: LibraryBook): Boolean {
        return LibraryClient.updateBook(handler, book)
    }

    fun deleteBook(uuid: UUID): Boolean {
        return LibraryClient.deleteBook(handler, uuid)
    }

    fun getStudentGrade(): List<_GradeItem> {
        return listOf(
            _GradeItem(
                "信号与系统",60,4.000,"B09G1010"),
            _GradeItem(
                "信号与系统",60,4.000,"B09G1010"),
            _GradeItem(
                "信号与系统",60,4.000,"B09G1010"),
        )
    }

    fun getAllStoreItems(): List<StoreItem> {
        return StoreClient.getAll(handler)
    }

    fun getAllSchedule(): List<_TeachingClass> {
        return listOf(
                _TeachingClass(listOf(Pair(Pair(1, 16), Pair(1, Pair(2, 4)))),
                        "sig", 111, "九龙湖"),
                _TeachingClass(listOf(Pair(Pair(1, 16), Pair(2, Pair(6, 7)))),
                        "alg", 222, "九龙湖"),
                _TeachingClass(listOf(Pair(Pair(1, 16), Pair(3, Pair(8, 10)))),
                        "ds", 333, "九龙湖"),
                _TeachingClass(listOf(Pair(Pair(1, 16), Pair(4, Pair(11, 13)))),
                        "os", 444, "九龙湖"),

        )
    }

    fun getAllOrder(): List<_StoreOrder> {
        return listOf(
                _StoreOrder("2023年9月1日", listOf(
                        _StoreItem(
                                "索尼国行PS5主机PlayStation电视游戏机蓝光8K港版日版现货闪送",
                                279900, "thisisbarcode", 10),
                        _StoreItem(
                                "微软Xbox Series S/X 国行主机 XSS XSX 日欧版 次时代4K游戏主机",
                                190000, "thisisbarcode", 10),
                        _StoreItem(
                                "任天堂Switch OLED日版主机NS续航港版健身朱紫塞尔达限定游戏机",
                                163000, "thisisbarcode", 10),
                        _StoreItem(
                                "华硕RTX4090猛禽ROG玩家国度电竞特工台式机电脑游戏独立显卡",
                                1414900, "thisisbarcode", 10),
                )),
                _StoreOrder("2023年8月1日", listOf(
                        _StoreItem(
                                "索尼国行PS5主机PlayStation电视游戏机蓝光8K港版日版现货闪送",
                                279900, "thisisbarcode", 10),
                        _StoreItem(
                                "微软Xbox Series S/X 国行主机 XSS XSX 日欧版 次时代4K游戏主机",
                                190000, "thisisbarcode", 10),
                        _StoreItem(
                                "任天堂Switch OLED日版主机NS续航港版健身朱紫塞尔达限定游戏机",
                                163000, "thisisbarcode", 10),
                        _StoreItem(
                                "华硕RTX4090猛禽ROG玩家国度电竞特工台式机电脑游戏独立显卡",
                                1414900, "thisisbarcode", 10),
                )),
                _StoreOrder("2023年7月1日", listOf(
                        _StoreItem(
                                "索尼国行PS5主机PlayStation电视游戏机蓝光8K港版日版现货闪送",
                                279900, "thisisbarcode", 10),
                        _StoreItem(
                                "微软Xbox Series S/X 国行主机 XSS XSX 日欧版 次时代4K游戏主机",
                                190000, "thisisbarcode", 10),
                        _StoreItem(
                                "任天堂Switch OLED日版主机NS续航港版健身朱紫塞尔达限定游戏机",
                                163000, "thisisbarcode", 10),
                        _StoreItem(
                                "华硕RTX4090猛禽ROG玩家国度电竞特工台式机电脑游戏独立显卡",
                                1414900, "thisisbarcode", 10),
                ))
        )
    }
}