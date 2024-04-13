package ru.axas.core.base.compose_items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import ru.axas.core.theme.ThemeApp
import ru.axas.core.theme.res.DimApp


@Composable
fun ButtonRowOutLine(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    text: String,
    contentEnd: (@Composable () -> Unit)? = null,
    contentStart: (@Composable () -> Unit)? = null,
    contentColor: Color = ThemeApp.colors.black,
    borderColor: Color = contentColor,
    containerColor: Color = ThemeApp.colors.backgroundMain,
    shape: Shape = ThemeApp.shape.smallAll,
    textAlign: TextAlign = TextAlign.Center,
    styleContent: TextStyle = ThemeApp.typography.medium,
    minLinesForeHeight: Int = 3,
) {

    Row(
        modifier = modifier
            .clip(shape)
            .minLinesHeight(
                minLines = minLinesForeHeight,
                textStyle = styleContent)
            .background(containerColor)
            .border(
                width = DimApp.lineHeight,
                color = borderColor,
                shape = shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                enabled = isEnabled,
                role = Role.Button,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (contentStart != null) Box(
            modifier = Modifier
                .padding(start = DimApp.textPaddingMin)) {
            contentStart()
        }

        Text(
            modifier = Modifier
                .padding(DimApp.screenPadding)
                .fillMaxWidth()
                .weight(1f),
            textAlign = textAlign,
            style = styleContent,
            color = contentColor,
            text = text)

        if (contentEnd != null) Box(
            modifier = Modifier
                .padding(end = DimApp.textPaddingMin)) {
            contentEnd()
        }
    }
}
