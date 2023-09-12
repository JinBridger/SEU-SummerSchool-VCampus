package app.vcampus.client.scene.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

/**
 * selectable chip component
 *
 * @param selected state of select
 * @param text the text of chip
 * @param onClick function when click
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun selectableChip(selected: MutableState<Boolean>, text: String, onClick: (() -> Unit)? = null) {
    Chip(onClick = {
        selected.value = !selected.value
        onClick?.invoke()
    }, border = ChipDefaults.outlinedBorder,
        colors = run {
            if (selected.value) {
                ChipDefaults.chipColors(backgroundColor = MaterialTheme.colors.primary, contentColor = Color.White)
            } else {
                ChipDefaults.chipColors(backgroundColor = Color.White)
            }
        }) {
        Text(text)
    }
}