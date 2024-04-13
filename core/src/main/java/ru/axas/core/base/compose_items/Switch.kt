package ru.axas.core.base.compose_items

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.axas.core.theme.ThemeApp

@Composable
fun SwitchApp(
    modifier: Modifier = Modifier,
    isCheck: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    isEnabled: Boolean = true,

    ) {
    Switch(
        checked = isCheck,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = isEnabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = ThemeApp.colors.white,
            checkedTrackColor = ThemeApp.colors.primary,
            uncheckedThumbColor = ThemeApp.colors.black,
            uncheckedTrackColor = ThemeApp.colors.black.copy(.25f),
            disabledCheckedThumbColor = ThemeApp.colors.primary.copy(.5f),
            disabledCheckedTrackColor = ThemeApp.colors.white,
            disabledUncheckedThumbColor = ThemeApp.colors.primary.copy(.5f),
            disabledUncheckedTrackColor = ThemeApp.colors.attentionContent,
            uncheckedBorderColor = ThemeApp.colors.black.copy(.0f)

        )
    )
}

@Composable
fun SwitchAppNew(
    modifier: Modifier = Modifier,
    isCheck: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    isEnabled: Boolean = true,

    ) {
    Switch(
        checked = isCheck,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = isEnabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = ThemeApp.colors.primary,
            checkedTrackColor = ThemeApp.colors.primary,
            uncheckedThumbColor = ThemeApp.colors.primary,
            uncheckedTrackColor = ThemeApp.colors.primary.copy(.25f),
            disabledCheckedThumbColor = ThemeApp.colors.primary.copy(.5f),
            disabledCheckedTrackColor = ThemeApp.colors.white,
            disabledUncheckedThumbColor = ThemeApp.colors.black.copy(.5f),
            disabledUncheckedTrackColor = ThemeApp.colors.attentionContent,
        )
    )
}

