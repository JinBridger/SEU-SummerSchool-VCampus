package app.vcampus.client.scene.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vcampus.client.repository.copy
import app.vcampus.client.viewmodel.ShopViewModel
import app.vcampus.server.entity.StoreItem

/**
 * shop item card component, used in `SelectItemSubscene`
 *
 * @param item the store item
 * @param viewModel the viewmodel of subscene
 */
@Composable
fun shopItemCard(item: StoreItem, viewModel: ShopViewModel) {
    Card(
        modifier = Modifier.shadowCustom(
            blurRadius = 3.dp,
            shapeRadius = 3.dp
        ).height(340.dp).width(250.dp)
    ) {
        Column {
            Box(modifier = Modifier.aspectRatio(1F).fillMaxWidth()) {
                AsyncImage(
                    load = { loadImageBitmap(item.pictureLink) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.weight(1F).fillMaxWidth().padding(
                        paddingValues = PaddingValues(
                            start = 8.dp, end = 8.dp,
                            top = 8.dp
                        )
                    )
                ) {
                    Text(
                        text = item.itemName,
                        fontWeight = FontWeight(700), maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier.weight(
                        1F
                    ).fillMaxWidth().padding(
                        8.dp
                    )
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("￥", fontSize = 14.sp)
                    }
                    Text(
                        String.format("%.2f", item.price / 100.0),
                        fontWeight = FontWeight(700),
                        fontSize = 24.sp
                    )
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = {
                            viewModel.selectItem.chosenShopItems.forEachIndexed { index, it ->
                                if (it.uuid == item.uuid) {
                                    viewModel.selectItem.chosenShopItems[index] = it.copy(
                                        stock = it.stock + 1
                                    )
                                    viewModel.chosenItemsCount.value += 1
                                    viewModel.chosenItemsPrice.value += it.price
                                }
                            }
                        }, shape = CircleShape,
                        modifier = Modifier.aspectRatio(1F),
                        contentPadding = PaddingValues(2.dp)
                    ) {
                        Icon(Icons.Default.Add, "")
                    }
                }
            }
        }
    }
}