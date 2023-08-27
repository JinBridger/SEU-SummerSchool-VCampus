package app.vcampus.client.scene

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import app.vcampus.client.scene.components.NavShape
import app.vcampus.client.viewmodel.MainPanelViewModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModel


@ExperimentalMaterialApi
@Composable
fun MainPanelScene(
    id: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit,
) {
    val viewModel = viewModel(MainPanelViewModel::class, listOf(id)) {
        MainPanelViewModel(id)
    }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "欢迎来到 VCampus 自助服务大厅！",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            letterSpacing = 0.15.sp,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { onEdit.invoke() }) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    }
                },
            )
        },
        drawerShape = NavShape(0.dp, 0.3f),
        drawerContent = {
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            Box(Modifier.width(800.dp).align(Alignment.TopCenter)) {
                LazyColumn {
                    item {
                        Spacer(Modifier.height(50.dp))
                        Text(
                            text = "晚上好，测试用户！",
                            style = TextStyle(
                                fontSize = 34.sp,
                                lineHeight = 36.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xDE000000),
                            )
                        )
                        Text(
                            text = "今天想做些什么？",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0x99000000),
                                letterSpacing = 0.25.sp,
                            )
                        )
                    }
                }
            }
        }

    }
}