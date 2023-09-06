package app.vcampus.client.repository

import app.vcampus.client.gateway.AuthClient
import app.vcampus.client.gateway.LibraryClient
import app.vcampus.client.gateway.StoreClient
import app.vcampus.client.gateway.StudentStatusClient
import app.vcampus.client.net.NettyHandler
import app.vcampus.server.entity.LibraryBook
import app.vcampus.server.entity.StoreItem
import app.vcampus.server.entity.StoreTransaction
import app.vcampus.server.entity.Student
import app.vcampus.server.entity.User
import app.vcampus.server.utility.Pair
import moe.tlaster.precompose.viewmodel.ViewModel
import mu.KotlinLogging
import java.util.UUID


data class _GradeItem(
    val courseName: String, val grade: Int,
    val credit: Double, val courseId: String)


data class _StoreItem(
    val itemName: String, val price: Int,
    val barcode: String, val stock: Int,
    val description:String)

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

    fun searchStudent(keyword: String): List<Student> {
        return StudentStatusClient.searchInfo(handler, keyword)
    }

    fun updateStudent(student: Student): Boolean {
        return StudentStatusClient.updateInfo(handler, student)
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

    fun searchStoreItem(keyword: String): Map<String, List<StoreItem>>{
        return StoreClient.searchItem(handler,keyword) ?: mapOf()
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

    fun getAllOrder(): Map<String, List<StoreTransaction>> {
        return StoreClient.getTransaction(handler)
    }

    fun getStoreItemByUuid(uuid: String): StoreItem {
        return StoreClient.searchId(handler, uuid)
    }

    fun getOneNewStoreItem():_StoreItem {
        return _StoreItem(
            "索尼国行PS5主机PlayStation电视游戏机蓝光8K港版日版现货闪送",
            279900,"",10,"你买不了吃亏买不了上当"
        )
    }
}