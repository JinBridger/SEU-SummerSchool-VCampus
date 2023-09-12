package app.vcampus.client.scene.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
 * shop check list item component, used in `SelectItemSubscene`
 *
 * @param item the store item
 * @param viewModel the viewmodel of subscene
 */
@Composable
fun shopCheckListItem(item: StoreItem, viewModel: ShopViewModel) {
    Column {
        Row(modifier = Modifier.fillMaxWidth().height(150.dp).padding(6.dp)) {
            Box(
                modifier = Modifier.aspectRatio(1F).fillMaxHeight().clip(
                    RoundedCornerShape(4.dp)
                )
            ) {
                AsyncImage(
                    load = { loadImageBitmap(item.pictureLink) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = item.itemName,
                    fontWeight = FontWeight(700), maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1F))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            viewModel.selectItem.chosenShopItems.forEachIndexed { index, it ->
                                if (it.uuid == item.uuid) {
                                    viewModel.selectItem.chosenShopItems[index] = it.copy(
                                        stock = it.stock - 1
                                    )
                                    viewModel.chosenItemsCount.value -= 1
                                    viewModel.chosenItemsPrice.value -= it.price
                                }
                            }
                        },
                        modifier = Modifier.height(30.dp).aspectRatio(1F),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(Icons.Default.Remove, "")
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = Color.LightGray,
                            RoundedCornerShape(8.dp)
                        ).height(30.dp).width(
                            30.dp
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(item.stock.toString())
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    TextButton(
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
                        },
                        modifier = Modifier.height(30.dp).aspectRatio(1F),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(Icons.Default.Add, "")
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Spacer(modifier = Modifier.weight(1F))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text("￥", fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Text(
                        String.format("%.2f", item.price / 100.0), fontWeight = FontWeight(700),
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.weight(1F))
                }
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp,
            color = Color.LightGray
        )
    }
}