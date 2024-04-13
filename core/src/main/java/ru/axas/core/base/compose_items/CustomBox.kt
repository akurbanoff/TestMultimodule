package ru.axas.core.base.compose_items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ru.axas.core.theme.res.DimApp

@Composable
@NonRestartableComposable
fun BoxSpacer(
    ratio: Float = 1f,
    size: Dp = DimApp.screenPadding
) {
    Box(modifier = Modifier.size(size * ratio))
}