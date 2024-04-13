package ru.axas.core.base.compose_items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import ru.axas.core.theme.res.DimApp

@Composable
fun IconApp(
    painter: Painter,
    modifier: Modifier = Modifier,
    size: Dp? = DimApp.iconSizeStandard,
    tint: Color? = LocalContentColor.current
) {
    tint?.let {
        Icon(
            modifier = modifier.then(size?.let { Modifier.size(size) } ?: Modifier),
            painter = painter,
            contentDescription = null,
            tint = tint,
        )
    } ?: run {
        Box(modifier = modifier
            .then(size?.let { Modifier.size(size) } ?: Modifier)
            .paint(
                painter = painter,
                contentScale = ContentScale.Fit))
    }
}