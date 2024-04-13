package ru.axas.core.base.compose_items

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.axas.core.theme.ThemeApp

@Composable
fun CheckerApp(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = ThemeApp.colors.primary,
        uncheckedColor = ThemeApp.colors.primary,
        checkmarkColor = ThemeApp.colors.white,
        disabledIndeterminateColor = ThemeApp.colors.white,
        disabledUncheckedColor = ThemeApp.colors.primary,
        disabledCheckedColor = ThemeApp.colors.primary
    ),
) {
    Checkbox(
        modifier = modifier,
        enabled = enabled,
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = colors
    )
}
