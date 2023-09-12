package app.vcampus.client.scene.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * top bar component, the bar on the top of UI
 *
 * @param heading the text in the bar
 * @param onClose function when close
 */
@Composable
fun TopBar(heading: String, onClose: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = heading,
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                    letterSpacing = 0.15.sp,
                    fontFamily = sarasaUiSc
                )
            )
        },
        actions = {
            IconButton(onClick = { onClose() }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        },
    )
}