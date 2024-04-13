package ru.axas.core.base.compose_items.crop

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.axas.core.base.compose_items.crop.AspectRatio
import ru.axas.core.base.extension.rememberState
import ru.axas.core.theme.ThemeApp

data class CropImageOption(
    val frameColor: Color = Color.White,
    val frameAlpha: Float = 0.8f,
    val frameWidth: Dp = 2.dp,
    val frameAspectRatio: AspectRatio? = null,
    val gridColor: Color = Color.White,
    val gridAlpha: Float = 0.6f,
    val gridWidth: Dp = 1.dp,
    val maskColor: Color = Color.Black,
    val maskAlpha: Float = 0.5f,
    val backgroundColor: Color = Color.Black,
)

@Composable
fun rememberCropImageOption(
    gridColor: Color = ThemeApp.colors.primary
) = rememberState {
    CropImageOption().copy(
        frameAspectRatio = AspectRatio(1, 1),
        gridColor = gridColor,
        frameColor = gridColor,
    )
}