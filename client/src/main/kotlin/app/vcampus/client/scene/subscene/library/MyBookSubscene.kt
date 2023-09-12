package app.vcampus.client.scene.subscene.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.bookList
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.LibraryViewModel

/**
 * my book subscene
 *
 * @param viewModel viewmodel of parent scene
 */
@Composable
fun myBookSubscene(viewModel: LibraryViewModel) {
    LaunchedEffect(Unit) {
        viewModel.myBook.init()
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("我的书籍", "查看已借阅书籍")
                Spacer(Modifier.height(20.dp))
                Text("当前借阅：${viewModel.myBook.currentBorrowed.size} / 最大借阅：20", fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
            }

            viewModel.myBook.currentBorrowed.forEach {
                item(it.uuid) {
                    bookList(it, viewModel.myBook::renewBook)
                    Spacer(Modifier.height(10.dp))
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

