package app.vcampus.client.scene.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun shopItemCard() {
    Card(modifier = Modifier.shadowCustom(blurRadius = 3.dp,
            shapeRadius = 3.dp).height(340.dp).width(250.dp)) {
        Column {
            Box(modifier = Modifier.aspectRatio(1F).fillMaxWidth()) {
                Image(painterResource("test_image.png"), "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds)
            }
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.weight(1F).fillMaxWidth().padding(
                        paddingValues = PaddingValues(start = 8.dp, end = 8.dp,
                                top = 8.dp))) {
                    Text(text = "日行现货索尼PS5家用游戏机PLAYSTATION5官方正品百亿补贴假一赔十",
                            fontWeight = FontWeight(700), maxLines = 2,
                            overflow = TextOverflow.Ellipsis)
                }
                Row(modifier = Modifier.weight(
                        1F).fillMaxWidth().padding(
                        8.dp)) {
                    Column {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("￥", fontSize = 14.sp)
                    }
                    Text("2799.00", fontWeight = FontWeight(700),
                            fontSize = 24.sp)
                    Spacer(Modifier.weight(1f))
                    Button(onClick = {}, shape = CircleShape,
                            modifier = Modifier.aspectRatio(1F),
                            contentPadding = PaddingValues(2.dp)) {
                        Icon(Icons.Default.Add, "")
                    }
                }
            }
        }
    }
}