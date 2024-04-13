package ru.axas.core.base.compose_items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.axas.core.theme.ThemeApp
import ru.axas.core.theme.res.DimApp

@Composable
fun CodeFieldBoxChar(
    modifier: Modifier = Modifier,
    text: String,
    index: Int,
    background: Color = ThemeApp.colors.white,
    indicatorUnFocusedColor: Color = ThemeApp.colors.black,
    indicatorFocusedColor: Color = ThemeApp.colors.black,
    contentColor: Color = ThemeApp.colors.primary,
    style: TextStyle = ThemeApp.typography.medium,
    shape: Shape = ThemeApp.shape.smallAll,
    sizeBoxHeight: Dp = 60.dp,
    sizeBoxWidth: Dp = 65.dp,
    border: Dp = DimApp.lineHeight,
) {

    val isFocused = text.length == index
    val char = when {
        index == text.length -> "|"
        index > text.length -> ""
        else -> text.getOrNull(index).toString()
    }
    var alpha by remember { mutableStateOf(0.5f) }

    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(500)
            alpha = if (alpha > 0.1f) 0f else 0.5f
        }
    })

    Box(
        modifier = modifier
            .size(
                width = sizeBoxHeight,
                height = sizeBoxWidth
            )
            .clip(shape)
            .border(
                border = when {
                    isFocused -> BorderStroke(border, indicatorUnFocusedColor)
                    else -> BorderStroke(border, indicatorFocusedColor)
                },
                shape = shape
            )
            .background(background)
            .padding(border * 3),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char,
            style = style,
            color = if (isFocused) {
                contentColor.copy(alpha)
            } else {
                contentColor
            },
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}