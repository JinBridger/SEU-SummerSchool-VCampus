package app.vcampus.client.repository

import androidx.compose.ui.awt.ComposeWindow
import app.vcampus.client.gateway.*
import app.vcampus.client.net.NettyHandler
import app.vcampus.server.entity.*
import app.vcampus.server.utility.Pair
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import mu.KotlinLogging
import java.util.*

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
    lateinit var handler: NettyHandler;
    var isConnected = false

    lateinit var user: User;
    private val logger = KotlinLogging.logger {}
    lateinit var window: ComposeWindow

    val gptJfxPanel = JFXPanel()

    fun initGptWebview() {
        Platform.runLater {
            val view = WebView()

            gptJfxPanel.scene = Scene(view)
            view.engine.load("https://gpt.seumsc.com")
            if (System.getProperty("os.name").startsWith("Windows")) {
                view.engine.userStyleSheetLocation = "data:,body { font-family: 'Microsoft YaHei'; }"
            }
        }
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

    fun searchTransaction(keyword: String): Map<String, List<StoreTransaction>> {
        return StoreClient.searchTransaction(handler, keyword) ?: mapOf()
    }

    fun getAllStoreItems(): List<StoreItem> {
        return StoreClient.getAll(handler)
    }

    fun buyItems(items: List<Pair<UUID, Int>>): Boolean {
        return StoreClient.buyItems(handler, items)
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

    fun getTodaySalesVolume(): Int {
        return StoreClient.getTodaySalesVolume(handler)
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