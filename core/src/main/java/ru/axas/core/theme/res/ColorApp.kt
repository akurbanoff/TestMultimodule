package ru.axas.core.theme.res

import androidx.compose.ui.graphics.Color
import ru.axas.core.theme.ColorScheme

object ColorApp {
    val Primary = Color(0xFF62D7C8)
    val BackgroundMain = Color(0xFFF3F5F9)
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)
    val TextBlack = Color(0xFF1D192B)
    val Color26 = Color(0xFF262626)
    val Color74 = Color(0xFF747474)
    val TextGray = Color(0xFF726F7C)
    val Red100 = Color(0xFFFF4F50)
    val TextError = Color(0xFFD76270)
    val SlateGray = Color(0xFF6B717B)
    val Crimson = Color(0xFFB3261E)

    val LightPalette = ColorScheme(
        primary = Primary,
        backgroundMain = BackgroundMain,
        white = White,
        textColor26 = Color26,
        textColor74 = Color74,
        textGrey = TextGray,
        textBlack = TextBlack,
        textError = TextError,
        black = Black,
        attentionContent = Red100,
        slateGrey = SlateGray,
        error = Crimson,
    )
}