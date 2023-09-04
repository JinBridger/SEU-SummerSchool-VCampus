package app.vcampus.client.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import app.vcampus.client.repository.FakeRepository
import app.vcampus.client.scene.components.SideBarItem
import app.vcampus.server.entity.LibraryBook
import app.vcampus.server.enums.BookStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import java.util.UUID

data class MutableLibraryBook(
    var uuid: UUID = UUID.randomUUID(),
    var isbn: MutableState<String> = mutableStateOf(""),
    var name: MutableState<String> = mutableStateOf(""),
    var author: MutableState<String> = mutableStateOf(""),
    var press: MutableState<String> = mutableStateOf(""),
    var description: MutableState<String> = mutableStateOf(""),
    var place: MutableState<String> = mutableStateOf(""),
    var cover: MutableState<String> = mutableStateOf(""),
    var callNumber: MutableState<String> = mutableStateOf(""),
    var bookStatus: MutableState<BookStatus> = mutableStateOf(BookStatus.available)
) {
    fun toLibraryBook(): LibraryBook {
        val newBook = LibraryBook()

        newBook.uuid = uuid
        newBook.isbn = isbn.value
        newBook.name = name.value
        newBook.author = author.value
        newBook.press = press.value
        newBook.description = description.value
        newBook.place = place.value
        newBook.bookStatus = bookStatus.value
        newBook.cover = cover.value
        newBook.callNumber = callNumber.value

        return newBook
    }

    fun fromLibraryBook(book: LibraryBook) {
        uuid = book.uuid ?: UUID.randomUUID()
        isbn.value = book.isbn ?: ""
        name.value = book.name ?: ""
        author.value = book.author ?: ""
        press.value = book.press ?: ""
        description.value = book.description ?: ""
        place.value = book.place ?: ""
        bookStatus.value = book.bookStatus ?: BookStatus.available
        cover.value = book.cover ?: ""
        callNumber.value = book.callNumber ?: ""
    }
}

class LibraryViewModel : ViewModel() {
    val identity = FakeRepository.user.roles.toList()
    val addBook = AddBook()
    val searchBook = SearchBook()
    val modifyBook = ModifyBook()

    val sideBarContent = (if (identity.contains("library_user")) {
        listOf(SideBarItem(true, "查询", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "library_user" -> listOf(
                SideBarItem(
                    false, "查询图书", "查找图书馆藏书",
                    Icons.Default.Search, false
                ),
                SideBarItem(
                    false, "我的书籍", "查看已借阅书籍",
                    Icons.Default.MenuBook, false
                )
            )

            else -> emptyList()
        }
    } + (if (identity.contains("library_staff")) {
        listOf(SideBarItem(true, "管理工具", "", Icons.Default.Info, false))
    } else {
        emptyList()
    }) + identity.flatMap {
        when (it) {
            "library_staff" -> listOf(
                SideBarItem(
                    false, "添加图书", "添加新的图书",
                    Icons.Default.LibraryAdd,
                    false
                ),
                SideBarItem(
                    false, "修改图书", "修改现有图书",
                    Icons.Default.AutoFixHigh,
                    false
                ),
                SideBarItem(
                    false, "办理借还书", "办理借还书业务",
                    Icons.Default.Queue,
                    false
                )
            )

            else -> emptyList()
        }
    }


    val librarySideBarItem = sideBarContent.toMutableStateList()

    class AddBook : ViewModel() {
        var showDetails = mutableStateOf(false)
        var newBook = mutableStateOf(MutableLibraryBook())

        var showMessage = mutableStateOf(false)
        var result = mutableStateOf(true)

        fun preAddBook() {
            showDetails.value = false

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    preAddBookInternal().collect {
                        newBook.value.fromLibraryBook(it)

                        showDetails.value = true
                    }
                }
            }
        }

        private suspend fun preAddBookInternal() = flow {
            emit(FakeRepository.preAddBook(newBook.value.isbn.value))
        }

        fun addBook() {
            showMessage.value = false

            if (newBook.value.name.value == "" || newBook.value.isbn.value == "" || newBook.value.author.value == "" ||
                newBook.value.press.value == "" || newBook.value.description.value == "" || newBook.value.place.value == "" || newBook.value.callNumber.value == ""
            ) {
                showMessage.value = true
                result.value = false
                return
            }

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    addBookInternal(newBook.value.toLibraryBook()).collect {
                        result.value = it
                        showMessage.value = true
                    }
                }
            }
        }

        private suspend fun addBookInternal(newBook: LibraryBook) = flow {
            emit(FakeRepository.addBook(newBook))
        }
    }

    open class SearchBook : ViewModel() {
        val keyword = mutableStateOf("")
        val bookList = mutableStateMapOf<String, List<LibraryBook>>()
        var searched = mutableStateOf(false)

        fun searchBook() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    searchBookInternal().collect {
                        bookList.clear()
                        bookList.putAll(it)

                        searched.value = true
                    }
                }
            }
        }

        private suspend fun searchBookInternal() = flow {
            emit(FakeRepository.searchBook(keyword.value))
        }
    }

    class ModifyBook : SearchBook() {
        fun updateBook(book: LibraryBook) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    updateBookInternal(book).collect {

                    }
                }
            }
        }

        private suspend fun updateBookInternal(book: LibraryBook) = flow {
            emit(FakeRepository.updateBook(book))
        }

        fun deleteBook(uuid: UUID) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    deleteBookInternal(uuid).collect {

                    }
                }
            }
        }

        private suspend fun deleteBookInternal(uuid: UUID) = flow {
            emit(FakeRepository.deleteBook(uuid))
        }
    }
}
