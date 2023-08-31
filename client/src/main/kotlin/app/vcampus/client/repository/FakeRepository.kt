package app.vcampus.client.repository

import app.vcampus.client.gateway.AuthClient
import app.vcampus.client.gateway.LibraryClient
import app.vcampus.client.gateway.StudentStatusClient
import app.vcampus.client.net.NettyHandler
import app.vcampus.server.entity.LibraryBook
import app.vcampus.server.entity.Student
import app.vcampus.server.entity.User

object FakeRepository {
    private lateinit var handler: NettyHandler;
    lateinit var user: User;

    fun setHandler(handler: NettyHandler) {
        this.handler = handler
    }

    fun login(username: String, password: String): Boolean {
        val user = AuthClient.login(handler, username, password)

        user?.let {
            print(it)
            this.user = it
            return true
        }

        return false
    }

    fun getSelf(): Student {
        return StudentStatusClient.getSelf(handler)
    }

    fun preAddBook(isbn: String): LibraryBook {
        val book = LibraryClient.preAddBook(handler, isbn)

        book?.let {
            return it
        }
        
        return LibraryBook()
    }
}