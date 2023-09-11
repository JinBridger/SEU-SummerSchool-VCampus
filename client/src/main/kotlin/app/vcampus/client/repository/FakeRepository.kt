package app.vcampus.client.repository

import app.vcampus.client.gateway.AdminClient
import app.vcampus.client.gateway.AuthClient
import app.vcampus.client.gateway.FinanceClient
import app.vcampus.client.gateway.LibraryClient
import app.vcampus.client.gateway.StoreClient
import app.vcampus.client.gateway.StudentStatusClient
import app.vcampus.client.gateway.TeachingAffairsClient
import app.vcampus.client.net.NettyHandler
import app.vcampus.server.entity.CardTransaction
import app.vcampus.server.entity.Course
import app.vcampus.server.entity.FinanceCard
import app.vcampus.server.entity.LibraryBook
import app.vcampus.server.entity.LibraryTransaction
import app.vcampus.server.entity.StoreItem
import app.vcampus.server.entity.StoreTransaction
import app.vcampus.server.entity.Student
import app.vcampus.server.entity.TeachingClass
import app.vcampus.server.entity.User
import app.vcampus.server.utility.Pair
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
    lateinit var window: ComposeWindow

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

    fun borrowBook(bookUuid: String, cardNumber: String): Boolean {
        return LibraryClient.borrowBook(handler, bookUuid, cardNumber)
    }

    fun getMyRecords(): List<LibraryTransaction> {
        return LibraryClient.getMyRecords(handler)
    }

    fun userRenewBook(uuid: UUID): Boolean {
        return LibraryClient.userRenewBook(handler, uuid)
    }

    fun staffGetRecords(cardNumber: String): List<LibraryTransaction> {
        return LibraryClient.staffGetRecords(handler, cardNumber)
    }

    fun staffReturnBook(uuid: UUID): Boolean {
        return LibraryClient.returnBook(handler, uuid)
    }

    fun staffRenewBook(uuid: UUID): Boolean {
        return LibraryClient.staffRenewBook(handler, uuid)
    }

    fun searchStoreItem(keyword: String): List<StoreItem> {
        return StoreClient.searchItem(handler, keyword) ?: listOf()
    }

    fun updateStoreItem(storeItem: StoreItem): Boolean {
        return StoreClient.updateItem(handler, storeItem)
    }

    fun addStoreItem(newStoreItem: StoreItem): Boolean {
        return StoreClient.addItem(handler, newStoreItem)
    }

    fun searchTransaction(keyword: String):Map<String,List<StoreTransaction>>{
        return StoreClient.searchTransaction(handler,keyword) ?: mapOf()
    }
    fun getStudentGrade(): List<_GradeItem> {
        return listOf(
            _GradeItem(
                "信号与系统", 60, 4.000, "B09G1010"
            ),
            _GradeItem(
                "信号与系统", 60, 4.000, "B09G1010"
            ),
            _GradeItem(
                "信号与系统", 60, 4.000, "B09G1010"
            ),
        )
    }

    fun getAllStoreItems(): List<StoreItem> {
        return StoreClient.getAll(handler)
    }

    fun buyItems(items: List<Pair<UUID, Int>>): Boolean {
        return StoreClient.buyItems(handler, items)
    }

    fun getFakeSchedule(): List<_TeachingClass> {
        return listOf(
            _TeachingClass(
                listOf(Pair(Pair(1, 16), Pair(1, Pair(2, 4)))),
                "sig", 111, "九龙湖"
            ),
            _TeachingClass(
                listOf(Pair(Pair(1, 16), Pair(2, Pair(6, 7)))),
                "alg", 222, "九龙湖"
            ),
            _TeachingClass(
                listOf(Pair(Pair(1, 16), Pair(3, Pair(8, 10)))),
                "ds", 333, "九龙湖"
            ),
            _TeachingClass(
                listOf(Pair(Pair(1, 16), Pair(4, Pair(11, 13)))),
                "os", 444, "九龙湖"
            ),

            )
    }

    fun getSelectedClasses(): List<TeachingClass> {
        return TeachingAffairsClient.getSelectedClasses(handler)
    }

    fun sendEvaluationResult(result: Pair<UUID, Pair<List<Int>, String>>): Boolean {
        return TeachingAffairsClient.sendEvaluationResult(handler, result)
    }

    fun getSelectableCourses(): List<Course> {
        return TeachingAffairsClient.getSelectableCourses(handler)
    }

    fun chooseClass(uuid: UUID): Boolean {
        return TeachingAffairsClient.chooseClass(handler, uuid)
    }

    fun dropClass(uuid: UUID): Boolean {
        return TeachingAffairsClient.dropClass(handler, uuid)
    }

    fun getMyTeachingClasses(): List<TeachingClass> {
        return TeachingAffairsClient.getMyTeachingClasses(handler)
    }

    fun exportStudentList(tc: TeachingClass): String {
        return TeachingAffairsClient.exportStudentList(handler, tc.uuid)
    }

    fun exportGradeTemplate(tc: TeachingClass): String {
        return TeachingAffairsClient.exportGradeTemplate(handler, tc.uuid)
    }

    fun importGrade(tc: TeachingClass, file: String): Boolean {
        return TeachingAffairsClient.importGrade(handler, tc.uuid, file)
    }

    fun getAllOrder(): Map<String, List<StoreTransaction>> {
        return StoreClient.getTransaction(handler) ?: mapOf()
    }

    fun getAllStoreTransactions(): List<StoreTransaction> {
        return StoreClient.getAllTransaction(handler) ?: listOf()
    }

    fun getStoreItemByUuid(uuid: String): StoreItem {
        return StoreClient.searchId(handler, uuid)
    }

    fun getTodaySalesVolume(): Int {
        return StoreClient.getTodaySalesVolume(handler)
    }

    fun getOneNewStoreItem(): _StoreItem {
        return _StoreItem(
            "索尼国行PS5主机PlayStation电视游戏机蓝光8K港版日版现货闪送",
            279900, "", 10, "你买不了吃亏买不了上当"
        )
    }

    fun getMyCard(): FinanceCard {
        return FinanceClient.getMyCard(handler)
    }

    fun getMyBills(): List<CardTransaction> {
        return FinanceClient.getMyBills(handler)
    }

    fun getByCardNumber(cardNumber: String): FinanceCard {
        return FinanceClient.getByCardNumber(handler, cardNumber) ?: FinanceCard()
    }

    fun updateCard(card: FinanceCard): FinanceCard {
        return FinanceClient.updateCard(handler, card) ?: FinanceCard()
    }

    fun rechargeCard(cardNumber: Int, amount: Int): FinanceCard {
        return FinanceClient.rechargeCard(handler, cardNumber, amount) ?: FinanceCard()
    }

    fun addUser(user: User): Boolean {
        return AdminClient.addUser(handler, user)
    }

    fun searchUser(keyword: String): List<User> {
        return AdminClient.searchUser(handler, keyword) ?: listOf()
    }

    fun modifyUser(cardNumber: Int, password: String, roles: List<String>): Boolean {
        return AdminClient.modifyUser(handler, cardNumber, password, roles)
    }
}