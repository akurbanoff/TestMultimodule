package ru.axas.core.base.compose_items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import ru.axas.core.R
import ru.axas.core.base.extension.rememberImageRaw
import ru.axas.core.theme.ThemeApp
import ru.axas.core.theme.res.DimApp
import ru.axas.core.theme.res.TextApp


@Composable
fun DialogYesOrNo(
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    title: String,
    body: String,
    buttonCancel: String = TextApp.cancel,
    buttonOk: String = TextApp.next,
) {

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(ThemeApp.shape.mediumAll)
                .background(ThemeApp.colors.white)
                .padding(horizontal = DimApp.screenPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = ThemeApp.typography.medium)
                IconButtonApp(
                    modifier = Modifier.offset(x = DimApp.screenPadding),
                    onClick = onDismiss
                ) {
                    IconApp(painter = rememberImageRaw(R.raw.ic_close))
                }
            }

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = ThemeApp.typography.medium,
                text = body
            )
            BoxSpacer()
            BoxSpacer()
            Row(modifier = Modifier.fillMaxWidth()) {
                ButtonAccentTextApp(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = onDismiss,
                    text = buttonCancel
                )
                BoxSpacer()
                ButtonAccentApp(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = {
                        onClick.invoke()
                        onDismiss.invoke()
                    },
                    text = buttonOk
                )
            }
            BoxSpacer()
        }
    }
}
