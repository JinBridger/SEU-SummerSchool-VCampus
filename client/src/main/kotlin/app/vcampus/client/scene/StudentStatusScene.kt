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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.scene.components.NavShape
import app.vcampus.client.scene.components.navDrawer
import app.vcampus.client.scene.components.navDrawerItem
import app.vcampus.client.viewmodel.StudentStatusViewModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.viewmodel.viewModel

@ExperimentalMaterialApi
@Composable
fun StudentStatusScene(
    navi: Navigator
) {
    val viewModel = viewModel(StudentStatusViewModel::class, listOf()) {
        StudentStatusViewModel()
    }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "学籍管理",
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
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    }
                },
            )
        },
        drawerShape = NavShape(0.dp, 0.3f),
        drawerContent = {
            navDrawer(navi)
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