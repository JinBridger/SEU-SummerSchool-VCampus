package app.vcampus.client.scene.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun shopCheckListItem() {
    Column {
        Row(modifier = Modifier.fillMaxWidth().height(150.dp).padding(6.dp)) {
            Box(modifier = Modifier.aspectRatio(1F).fillMaxHeight().clip(
                    RoundedCornerShape(4.dp))) {
                Image(painterResource("test_image.png"), "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "日行现货索尼PS5家用游戏机PLAYSTATION5官方正品百亿补贴假一赔十",
                        fontWeight = FontWeight(700), maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.weight(1F))
                Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = {},
                            modifier = Modifier.height(30.dp).aspectRatio(1F),
                            contentPadding = PaddingValues(0.dp)) {
                        Icon(Icons.Default.Remove, "")
                    }

//                    Button(onClick = {}, shape = CircleShape,
//                            modifier = Modifier.height(20.dp).aspectRatio(1F),
//                            contentPadding = PaddingValues(2.dp)) {
//                        Icon(Icons.Default.Remove, "")
//                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.border(width = 1.dp,
                            color = Color.LightGray,
                            RoundedCornerShape(8.dp)).height(30.dp).width(
                            30.dp),
                            contentAlignment = Alignment.Center) {
                        Text("1")
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    TextButton(onClick = {},
                            modifier = Modifier.height(30.dp).aspectRatio(1F),
                            contentPadding = PaddingValues(0.dp)) {
                        Icon(Icons.Default.Add, "")
                    }
//
//                    Button(onClick = {}, shape = CircleShape,
//                            modifier = Modifier.height(20.dp).aspectRatio(1F),
//                            contentPadding = PaddingValues(2.dp)) {
//                        Icon(Icons.Default.Add, "")
//                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Spacer(modifier = Modifier.weight(1F))
                Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom) {
                    Column {
                        Text("￥", fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Text("2799.00", fontWeight = FontWeight(700),
                            fontSize = 24.sp)
                    Spacer(modifier = Modifier.weight(1F))
                }
            }
        }
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp,
                color = Color.LightGray)
    }
}

@Composable
fun shopCheckList() {
    Card(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            (0..10).forEach {
                item {
                    shopCheckListItem()
                }
            }
        }
    }
}