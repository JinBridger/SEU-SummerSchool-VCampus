package app.vcampus.client.scene.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun selectableChip(selected: MutableState<Boolean>, text: String) {
    Chip(onClick = {selected.value = !selected.value}, border = ChipDefaults.outlinedBorder,
            colors = run {
                if(selected.value) {
                    ChipDefaults.chipColors(backgroundColor = MaterialTheme.colors.primary, contentColor = Color.White)
                } else {
                    ChipDefaults.chipColors(backgroundColor = Color.White)
                }
            }) {
        Text(text)
    }
}