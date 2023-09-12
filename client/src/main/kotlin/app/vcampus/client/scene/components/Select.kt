package app.vcampus.client.scene.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import app.vcampus.server.enums.LabelledEnum
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }

inline fun <reified T> T.callPrivateFunc(name: String, vararg args: Any?): Any? =
    T::class
        .declaredMemberFunctions
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.call(this, *args)

inline fun <reified T : Any, R> T.getPrivateProperty(name: String): R? =
    T::class
        .memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as? R

/**
 * select component
 *
 * @param T the type of items that could be selected
 * @param selectList the list of items that could be selected
 * @param label the label of textfield
 * @param setValue function when set the value
 * @param value preset value
 * @param basic whether it use basic or outlined
 * @param textStyle the text style
 * @param readOnly whether it is read only
 * @param modifier the modifier of the component
 */
@Composable
fun <T> Select(
    selectList: List<T>,
    label: @Composable (() -> Unit)? = null,
    setValue: ((T) -> Unit),
    value: T? = null,
    basic: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier.fillMaxWidth()
) where T : Enum<T>, T : LabelledEnum {
    var dropExpand by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(value ?: selectList[0]) }
    var dropDownWidth by remember { mutableStateOf(0) }

    val callBack = fun(t: T, expand: Boolean) {
        if (readOnly) return
        selected = t
        dropExpand = expand
        setValue(t)
    }

    val defaultTextFieldColors = TextFieldDefaults.textFieldColors()

    Column(modifier = modifier) {
        if (basic) {
            BasicTextField(
                value = selected.label,
                onValueChange = { },
                readOnly = true,
                enabled = false,
                textStyle = textStyle,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable { callBack(selected, true) }
                            .onSizeChanged { dropDownWidth = it.width },
                    ) {
                        innerTextField()
                        Icon(
                            imageVector = Icons.Outlined.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
            )
        } else {
            OutlinedTextField(
                selected.label,
                label = label,
                onValueChange = { },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable { callBack(selected, true) }
                    .onSizeChanged { dropDownWidth = it.width },
                textStyle = textStyle,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = defaultTextFieldColors.textColor(true).value,
                    disabledBorderColor = defaultTextFieldColors.indicatorColor(
                        true,
                        false,
                        remember { MutableInteractionSource() }).value,
                    disabledPlaceholderColor = defaultTextFieldColors.placeholderColor(true).value,
                    disabledLabelColor = defaultTextFieldColors.labelColor(
                        true,
                        false,
                        remember { MutableInteractionSource() }).value,
                    disabledLeadingIconColor = defaultTextFieldColors.leadingIconColor(true, false).value,
                    disabledTrailingIconColor = defaultTextFieldColors.trailingIconColor(true, false).value
                )
            )
        }

        DropdownMenu(
            expanded = dropExpand,
            onDismissRequest = {
                dropExpand = false
                callBack(selected, false)
            },
            modifier = Modifier.width(with(LocalDensity.current) { dropDownWidth.toDp() })
        ) {
            SelectOption(callBack, selectList)
        }
    }

}

@Composable
fun <T> SelectOption(callBack: (T, Boolean) -> Unit, selectList: List<T>) where T : Enum<T>, T : LabelledEnum {
    for (it in selectList) {
        DropdownMenuItem(onClick = {
            callBack(it, false)
        }) {
            Text(it.label)
        }
    }
}