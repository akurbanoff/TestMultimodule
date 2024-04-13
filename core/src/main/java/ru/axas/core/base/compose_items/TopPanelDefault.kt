package ru.axas.core.base.compose_items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import ru.axas.core.R
import ru.axas.core.theme.ThemeApp

@Composable
fun TopPanelDefault(
    title: String,
    onClickBack:() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ThemeApp.colors.white),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButtonApp(onClick = onClickBack) {
            IconApp(painter = painterResource(id = R.drawable.ic_arrow_left))
        }

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = ThemeApp.typography.medium.copy(fontSize = 16.sp)
        )
    }
}