package app.vcampus.client

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * Normal headings for UI
 */
@Composable
fun headings(headingText: String, captionText: String) {
    Column {
        Text(
            text = headingText,
            style = TextStyle(
                fontSize = 34.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight(700),
                color = Color(0xDE000000),
                textAlign = TextAlign.Start,
            )
        )
        Text(
            text = captionText,
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

@Composable
fun <T>waterfallLists(items: List<T>) {

}