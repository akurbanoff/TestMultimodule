package ru.axas.core.base.compose_items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import ru.axas.core.theme.ThemeApp

@Composable
fun ChipsApp(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ThemeApp.shape.smallAll,
    border: BorderStroke? = FilterChipDefaults.filterChipBorder(
        enabled = enabled,
        selected = selected
    ),
    colors: SelectableChipColors = FilterChipDefaults.filterChipColors(
        containerColor = ThemeApp.colors.white,
        labelColor = ThemeApp.colors.textColor74,
        disabledContainerColor = ThemeApp.colors.white,
        disabledLabelColor = ThemeApp.colors.textColor74,
        selectedContainerColor = ThemeApp.colors.primary,
        selectedLabelColor = ThemeApp.colors.backgroundMain
    ),
    text: String
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
        shape = shape,
        border = border,
        colors = colors,
        label = {
            Text(
                style = ThemeApp.typography.medium,
                text = text,
            )
        }
    )
}