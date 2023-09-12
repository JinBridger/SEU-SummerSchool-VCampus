package app.vcampus.client.scene.components

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

/**
 * enter animation, used in scene switching
 *
 * @param content the scene content
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun enterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(
            initialOffsetX = { 40 }
        ) + expandHorizontally(
            expandFrom = Alignment.End
        ),
        exit = slideOutHorizontally() + shrinkHorizontally(),
        content = content,
        initiallyVisible = false
    )
}