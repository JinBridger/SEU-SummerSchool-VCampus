package app.vcampus.client.scene.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
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
import app.vcampus.server.entity.StoreTransaction

/**
 * shop transaction list item component, used in `MyOrderSubscene`
 *
 * @param st the store transaction
 */
@Composable
fun shopTransactionListItem(
    st: StoreTransaction,
) {
    val subStoreItem = st.item
    Column {
        Row(modifier = Modifier.fillMaxWidth().height(150.dp).padding(6.dp)) {
            Box(
                modifier = Modifier.aspectRatio(1F).fillMaxHeight().clip(
                    RoundedCornerShape(4.dp)
                )
            ) {
                AsyncImage(
                    load = { loadImageBitmap(subStoreItem.pictureLink) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = subStoreItem.itemName,
                    fontWeight = FontWeight(700), maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1F))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                        Text(st.amount.toString())
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Spacer(modifier = Modifier.width(6.dp))

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
                        String.format("%.2f", st.itemPrice / 100.0),
                        fontWeight = FontWeight(700),
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.weight(1F))
                }
            }
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth(), thickness = 1.dp,
        color = Color.LightGray
    )
}
//@Composable
//fun shopTransactionListItem(
//    item: StoreItem,
//    viewModel: ShopViewModel
//) {
//    Column {
//        Row(modifier = Modifier.fillMaxWidth().height(150.dp).padding(6.dp)) {
//            Box(modifier = Modifier.aspectRatio(1F).fillMaxHeight().clip(
//                RoundedCornerShape(4.dp))) {
//                AsyncImage(
//                    load = { loadImageBitmap(item.pictureLink) },
//                    painterFor = { remember { BitmapPainter(it) } },
//                    contentDescription = "",
//                    contentScale = ContentScale.FillBounds)
//            }
//            Spacer(modifier = Modifier.width(10.dp))
//            Column(modifier = Modifier.fillMaxWidth()) {
//                Text(text = item.itemName,
//                        fontWeight = FontWeight(700), maxLines = 1,
//                        overflow = TextOverflow.Ellipsis)
//                Spacer(modifier = Modifier.weight(1F))
//                Row(modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.End,
//                        verticalAlignment = Alignment.CenterVertically) {
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Box(modifier = Modifier.border(width = 1.dp,
//                            color = Color.LightGray,
//                            RoundedCornerShape(8.dp)).height(30.dp).width(
//                            30.dp),
//                            contentAlignment = Alignment.Center) {
//                        Text(item.salesVolume.toString())
//                    }
//                    Spacer(modifier = Modifier.width(6.dp))
//                }
//                Spacer(modifier = Modifier.width(6.dp))
//
//            Spacer(modifier = Modifier.weight(1F))
//            Row(modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.Bottom) {
//                Column {
//                    Text("￥", fontSize = 14.sp)
//                    Spacer(modifier = Modifier.height(10.dp))
//                }
//                Text(String.format("%.2f", item.price / 100.0),
//                        fontWeight = FontWeight(700),
//                        fontSize = 24.sp)
//                Spacer(modifier = Modifier.weight(1F))
//            }
//            }
//        }
//    }
//    Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp,
//            color = Color.LightGray)
//}
