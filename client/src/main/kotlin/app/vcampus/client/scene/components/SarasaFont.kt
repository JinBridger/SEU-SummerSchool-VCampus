package app.vcampus.client.scene.components

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

val sarasaUiSc = FontFamily(
    Font(
        resource = "sarasa-ui-sc-regular.ttf",
        weight = FontWeight(400)
    ),
    Font(
        resource = "sarasa-ui-sc-bold.ttf",
        weight = FontWeight(900)
    ),
    Font(
        resource = "sarasa-ui-sc-semibold.ttf",
        weight = FontWeight(700)
    ),
)

/**
 * the Sarasa UI SC font typography
 */
val sarasaTypography = Typography(
    defaultFontFamily = sarasaUiSc,
)
