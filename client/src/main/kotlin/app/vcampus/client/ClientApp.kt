package app.vcampus.client

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import app.vcampus.client.headings
import kotlin.math.ceil

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    children: @Composable () -> Unit
) {
    Layout(
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}

@Composable
fun blankCard() {
    Card(
        Modifier
            .clickable{ }
            .width(350.dp)
            .height(145.dp),
        elevation = 5.dp,
    ) {
        Text("aaaaaaaa")
    }
}


@Composable
fun WaterfallList() {
    val items = (1..100).toList() // 生成一些数据

    val itemsList = (0..50).toList()
    val itemsIndexedList = listOf("A", "B", "C")

    val itemModifier = Modifier.border(1.dp, Color.Blue).height(80.dp).wrapContentSize()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 400.dp, vertical = 100.dp)
    ) {
        items(itemsList) {
            Text("Item is $it", itemModifier)
        }
        item {
            Text("Single item", itemModifier)
        }
        itemsIndexed(itemsIndexedList) { index, item ->
            Text("Item at index $index is $item", itemModifier)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun mainPanel() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Box(
            Modifier
                .width(1440.dp)
                .height(800.dp)
                .background(color = Color(0xFFFFFFFF))
        ) {
            Column {
                TopAppBar(
//                windowInsets = AppBarDefaults.topAppBarWindowInsets,
                    title = { Text(text) },
                    navigationIcon = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(Icons.Filled.Menu, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            text = "change!"
                        }) {
                            Icon(Icons.Filled.Notifications, contentDescription = "Localized description")
                        }
                    }
                )

                Box(
                    Modifier.width(600.dp)
                        .align(Alignment.CenterHorizontally)
//                        .padding(2.dp)
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .background(Color.Cyan),
                ) {
                    LazyVerticalStaggeredGrid (
//                        columns = GridCells.Fixed(2),
                        columns = StaggeredGridCells.Fixed(2),
//                      contentPadding = PaddingValues(horizontal = 300.dp, vertical = 100.dp),
                        verticalItemSpacing = 10.dp,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        itemsIndexed((0..50).toList()) { i, item ->
                            blankCard()
//                        Box(
//                            Modifier
//                                .padding(2.dp)
//                                .fillMaxWidth()
//                                .height(200.dp)
//                                .background(Color.Cyan),
//                        ) {
////                            blankCard()
//                        }
                        }
                    }
                }




//                StaggeredVerticalGrid(
//                    maxColumnWidth = 220.dp,
//                    modifier = Modifier.padding(4.dp)
//                ) {
//                    (0..50).toList().forEach { _ ->
//                        blankCard()
//                    }
//                }

//                LazyVerticalStaggeredGrid(
//                    columns = StaggeredGridCells.Fixed(2),
//                    contentPadding = PaddingValues(horizontal = 300.dp, vertical = 100.dp),
//                    verticalItemSpacing = 10.dp,
//                    horizontalArrangement = Arrangement.spacedBy(10.dp)
//                ) {
//                    itemsIndexed((0..50).toList()) { i, item ->
//                        blankCard()
//                    }
//                }
            }
        }

    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        resizable = false,
        state = WindowState(size = DpSize.Unspecified),
        title = "主界面 - VCampus"
    ) {
        mainPanel()
    }
}