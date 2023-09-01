package app.vcampus.client.repository

import app.vcampus.client.gateway.AuthClient
import app.vcampus.client.gateway.LibraryClient
import app.vcampus.client.gateway.StudentStatusClient
import app.vcampus.client.net.NettyHandler
import app.vcampus.server.entity.LibraryBook
import app.vcampus.server.entity.Student
import app.vcampus.server.entity.User

data class _StoreItem(val itemName: String, val price: Int,
                      val barcode: String, val stock: Int)

data class _StoreOrder(val date: String, val order: List<_StoreItem>)

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

    fun getAllStoreItems(): List<_StoreItem> {
        return listOf(
                _StoreItem(
                        "索尼国行PS5主机PlayStation电视游戏机蓝光8K港版日版现货闪送",
                        279900, "thisisbarcode", 100),
                _StoreItem(
                        "微软Xbox Series S/X 国行主机 XSS XSX 日欧版 次时代4K游戏主机",
                        190000, "thisisbarcode", 100),
                _StoreItem(
                        "任天堂Switch OLED日版主机NS续航港版健身朱紫塞尔达限定游戏机",
                        163000, "thisisbarcode", 100),
                _StoreItem(
                        "华硕RTX4090猛禽ROG玩家国度电竞特工台式机电脑游戏独立显卡",
                        1414900, "thisisbarcode", 100),
                _StoreItem(
                        "Intel/英特尔 第13代 i9 13900K 13900KF CPU 中文盒装处理器全新",
                        387900, "thisisbarcode", 100),
                _StoreItem(
                        "Apple/苹果 13 英寸 MacBook Air Apple M2 芯片 8 核中央处理器 8 核图形处理器 8GB 统一内存 256GB 固态硬盘",
                        899900, "thisisbarcode", 100),
                _StoreItem("Apple/苹果 iPhone 14 Pro", 799900, "thisisbarcode",
                        100),
                _StoreItem("Apple/苹果 11 英寸 iPad Pro", 679900,
                        "thisisbarcode", 100),
                _StoreItem(
                        "Asus/华硕ROG MAXIMUS Z790 HERO EVA二代台式机电脑电竞游戏主板",
                        509900, "thisisbarcode", 100),
                _StoreItem(
                        "芝奇DDR5内存条6000 6400 7600幻锋戟RGB台式电脑游戏16G/32G套装",
                        72900, "thisisbarcode", 100),
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