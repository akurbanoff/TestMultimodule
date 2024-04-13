package ru.axas.core.theme.res

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import ru.axas.core.R
import ru.axas.core.theme.TypographyScheme

object TypographyApp {

    val Typography = TypographyScheme(
        bold = TextStyle(fontFamily = FontFamily(Font(R.font.ubuntu_bold))),
        medium = TextStyle(fontFamily = FontFamily(Font(R.font.ubuntu_medium))),
        regular = TextStyle(fontFamily = FontFamily(Font(R.font.ubuntu_regular)))
    )
}