package app.vcampus.client.scene.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * page title component, H1 and H2
 *
 * @param heading H1 content
 * @param caption H2 content
 */
@Composable
fun pageTitle(heading: String, caption: String) {
    Column {
        Text(
            text = heading,
            style = TextStyle(
                fontSize = 34.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight(700),
                color = Color(0xDE000000),
                fontFamily = sarasaUiSc
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = caption,
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(400),
                color = Color(0x99000000),
                letterSpacing = 0.25.sp,
                fontFamily = sarasaUiSc
            )
        )
    }
}