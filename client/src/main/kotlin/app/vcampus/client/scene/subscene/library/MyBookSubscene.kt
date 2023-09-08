package app.vcampus.client.scene.subscene.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.myBookList
import app.vcampus.client.scene.components.pageTitle
import app.vcampus.client.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun myBookSubscene(viewModel: LibraryViewModel) {
//    var isText1Clicked by remember { mutableStateOf(false) }
//    var isText2Clicked by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(80.dp))
                pageTitle("我的书籍", "查看已借阅书籍")
                Spacer(Modifier.height(20.dp))
                Text("当前借阅：10 / 最大借阅：20", fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
            }

            (0..10).forEach {
                item {
                    myBookList()
                    Spacer(Modifier.height(10.dp))
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

