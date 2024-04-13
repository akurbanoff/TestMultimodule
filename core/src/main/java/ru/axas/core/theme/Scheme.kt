package ru.axas.core.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import ru.axas.core.theme.res.ColorApp
import ru.axas.core.theme.res.ShapesApp
import ru.axas.core.theme.res.TypographyApp

@Immutable
data class ColorScheme(
    val primary: Color,
    val backgroundMain: Color,
    val white: Color,
    val textColor26: Color,
    val textColor74: Color,
    val textGrey: Color,
    val textBlack: Color,
    val textError: Color,
    val black: Color,
    val attentionContent: Color,
    val slateGrey: Color,
    val error: Color
)

@Immutable
data class ShapeScheme(
    val smallAll: CornerBasedShape,
    val smallTop: CornerBasedShape,
    val smallBottom: CornerBasedShape,
    val smallRight: CornerBasedShape,
    val mediumAll: CornerBasedShape,
    val mediumTop: CornerBasedShape,
    val mediumBottom: CornerBasedShape,
    val mediumLeft: CornerBasedShape,
    val mediumRight: CornerBasedShape,
)

@Immutable
data class TypographyScheme(
    val bold: TextStyle,
    val medium: TextStyle,
    val regular: TextStyle
)

val LocalColorsAxas = staticCompositionLocalOf { ColorApp.LightPalette }
val LocalTypographyAxas = staticCompositionLocalOf { TypographyApp.Typography }
val LocalShapeAxas = staticCompositionLocalOf { ShapesApp.Shapes }

object ThemeApp {
    val colors: ColorScheme
        @Composable
        get() = LocalColorsAxas.current
    val typography: TypographyScheme
        @Composable
        get() = LocalTypographyAxas.current
    val shape: ShapeScheme
        @Composable
        get() = LocalShapeAxas.current
}