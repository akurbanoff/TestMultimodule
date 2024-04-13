package ru.axas.core.base.compose_items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.axas.core.R
import ru.axas.core.base.extension.rememberState
import ru.axas.core.theme.ThemeApp
import ru.axas.core.theme.TestMultimoduleTheme
import ru.axas.core.theme.res.DimApp

@Composable
fun ButtonAccentApp(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ThemeApp.shape.smallAll,
    colors: ColorButtonApp = colorsButtonAccentApp(),
    colorBackground: Color = if (enabled) ThemeApp.colors.primary else ThemeApp.colors.primary.copy(
        .2f
    ),
    buttonPadding: PaddingValues = PaddingValues(vertical = DimApp.buttonPadding),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentStart: @Composable RowScope.() -> Unit = {},
    contentEnd: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(buttonPadding)
            .defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = ButtonDefaults.MinHeight
            )
            .clip(shape)
            .background(
                colorBackground
            )
            .clickable(
                enabled = enabled,
                indication = rememberRipple(),
                interactionSource = interactionSource,
                role = Role.Button,
                onClick = onClick
            )
            .padding(contentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        contentStart.invoke(this)
        TextButtonMinLine(
            color = if (enabled) colors.contentColor else colors.disabledContentColor,
            text = text
        )
        contentEnd.invoke(this)
    }
}

@Composable
fun ButtonWeakApp(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ThemeApp.shape.smallAll,
    colors: ColorButtonApp = colorsButtonWeakApp(),
    textColor: Color = ThemeApp.colors.backgroundMain,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentStart: @Composable RowScope.() -> Unit = {},
    contentEnd: @Composable RowScope.() -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.containerColor,
            contentColor = colors.contentColor,
            disabledContainerColor = colors.disabledContainerColor,
            disabledContentColor = colors.disabledContentColor
        ),
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = {
            contentStart.invoke(this)
            TextButtonMinLine(
                text = text,
                color = textColor

            )
            contentEnd.invoke(this)
        },
    )
}

@Composable
fun ButtonWeakSquareApp(
    onClick: () -> Unit,
    painter: Painter = painterResource(id = R.drawable.ic_application),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ThemeApp.shape.smallAll,
    colors: ColorButtonApp = colorsButtonWeakApp(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val containerColor = if (enabled) colors.containerColor else colors.disabledContainerColor
    val contentColor = ThemeApp.colors.black

    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = border,
        interactionSource = interactionSource
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Row(
                Modifier
                    .size(ButtonDefaults.MinHeight)
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconApp(
                    painter = painter,
                    tint = LocalContentColor.current
                )
            }
        }
    }
}

@Composable
fun ButtonAccentTextApp(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ThemeApp.shape.smallAll,
    colors: ColorButtonApp = colorsButtonAccentTextApp(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentStart: @Composable RowScope.() -> Unit = {},
    contentEnd: @Composable RowScope.() -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.containerColor,
            contentColor = colors.contentColor,
            disabledContainerColor = colors.disabledContainerColor,
            disabledContentColor = colors.disabledContentColor
        ),
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = {
            contentStart.invoke(this)
            TextButtonMinLine(
                text = text,
                color = if (enabled) ThemeApp.colors.black else ThemeApp.colors.black.copy(
                    .2f
                )
            )
            contentEnd.invoke(this)
        },
    )
}


@Composable
fun TextButtonApp(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ThemeApp.shape.smallAll,
    styleText: TextStyle = ThemeApp.typography.medium,
    colors: ColorButtonApp = colorsButtonAccentTextApp(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    ifCorrectTextSize: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentStart: @Composable RowScope.() -> Unit = {},
    contentEnd: @Composable RowScope.() -> Unit = {}
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.containerColor.copy(0f),
            contentColor = colors.contentColor,
            disabledContainerColor = colors.disabledContainerColor.copy(0f),
            disabledContentColor = colors.disabledContentColor
        ),
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = {
            contentStart.invoke(this)

            if (ifCorrectTextSize) {
                TextButtonMinLine(
                    color = if (enabled) colors.contentColor else colors.disabledContentColor,
                    style = styleText,
                    text = text
                )
            }

            if (!ifCorrectTextSize) {
                Text(
                    text = text,
                    color = if (enabled) colors.contentColor else colors.disabledContentColor,
                    style = styleText,
                    softWrap = false,
                    maxLines = 1,
                )
            }
            contentEnd.invoke(this)
        },
    )
}


@Composable
fun IconButtonApp(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    conditional: () -> Boolean = { true },
    enabled: Boolean = true,
    isEnabledBrush: Boolean = false,
    colors: ColorButtonApp = colorsIconButtonApp(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {

    val rememberColor by rememberUpdatedState(
        newValue = if (isEnabledBrush) Modifier.background(
            ThemeApp.colors.primary,
            shape = CircleShape
        ) else Modifier
    )

    var onClickHasExecuted by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            if (conditional() && !onClickHasExecuted) {
                onClickHasExecuted = true
                onClick.invoke()
            }
        },
        modifier = modifier.then(rememberColor),
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colors.containerColor,
            contentColor = colors.contentColor,
            disabledContainerColor = colors.disabledContainerColor,
            disabledContentColor = colors.disabledContentColor
        ),
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
fun colorsButtonAccentApp(
    containerColor: Color = ThemeApp.colors.primary,
    contentColor: Color = ThemeApp.colors.black,
    disabledContainerColor: Color = ThemeApp.colors.primary.copy(.5f),
    disabledContentColor: Color = ThemeApp.colors.black
) = ColorButtonApp(
    containerColor = containerColor,
    contentColor = contentColor,
    disabledContainerColor = disabledContainerColor,
    disabledContentColor = disabledContentColor
)

@Composable
fun colorsButtonAccentTextApp(
    containerColor: Color = ThemeApp.colors.primary,
    contentColor: Color = ThemeApp.colors.black,
    disabledContainerColor: Color = ThemeApp.colors.primary.copy(.5f),
    disabledContentColor: Color = ThemeApp.colors.black
) = ColorButtonApp(
    containerColor = containerColor,
    contentColor = contentColor,
    disabledContainerColor = disabledContainerColor,
    disabledContentColor = disabledContentColor
)

@Composable
fun colorsIconButtonApp(
    containerColor: Color = Color.Transparent,
    contentColor: Color = ThemeApp.colors.black,
    disabledContainerColor: Color = Color.Transparent,
    disabledContentColor: Color = ThemeApp.colors.black
) = ColorButtonApp(
    containerColor = containerColor,
    contentColor = contentColor,
    disabledContainerColor = disabledContainerColor,
    disabledContentColor = disabledContentColor
)

data class ColorButtonApp(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
)

@Composable
fun colorsButtonWeakApp(
    containerColor: Color = ThemeApp.colors.primary,
    contentColor: Color = ThemeApp.colors.black,
    disabledContainerColor: Color = ThemeApp.colors.primary.copy(.5f),
    disabledContentColor: Color = ThemeApp.colors.black
) = ColorButtonApp(
    containerColor = containerColor,
    contentColor = contentColor,
    disabledContainerColor = disabledContainerColor,
    disabledContentColor = disabledContentColor
)

@Composable
private fun TextButtonMinLine(
    color: Color = ThemeApp.colors.backgroundMain,
    text: String,
    style: TextStyle = ThemeApp.typography.medium
) {
    var readyToDraw by rememberState { false }
    var textStyle by rememberState { style }
    Text(
        modifier = Modifier
            .drawWithContent {
                if (readyToDraw) drawContent()
            },
        text = text,
        color = color,
        style = textStyle,
        softWrap = false,
        maxLines = 1,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                textStyle =
                    textStyle.copy(fontSize = textStyle.fontSize * 0.95)
            } else {
                readyToDraw = true
            }
        },
    )
}

@Preview(backgroundColor = 0xFFBDAEAE)
@Composable
fun ButtonPreview() {
    TestMultimoduleTheme {
        Column(
            modifier = Modifier
                .background(ThemeApp.colors.backgroundMain)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BoxSpacer()
            ButtonAccentApp(
                onClick = { /*TODO*/ },
                text = "ButtonAccentApp"
            )
            BoxSpacer()
            ButtonAccentApp(
                enabled = false,
                onClick = { /*TODO*/ },
                text = "ButtonAccentApp"
            )
            BoxSpacer()
            ButtonWeakApp(
                onClick = { /*TODO*/ },
                text = "ButtonWeakApp"
            )
            BoxSpacer()
            ButtonWeakApp(
                enabled = false,
                onClick = { /*TODO*/ },
                text = "ButtonWeakApp"
            )
            BoxSpacer()
            ButtonWeakSquareApp(
                enabled = true,
                onClick = { /*TODO*/ },
            )
            BoxSpacer()
            ButtonAccentTextApp(
                onClick = { /*TODO*/ },
                text = "ButtonAccentTextApp"
            )
            BoxSpacer()
            ButtonAccentTextApp(
                enabled = false,
                onClick = { /*TODO*/ },
                text = "ButtonAccentTextApp"
            )
            BoxSpacer()
            TextButtonApp(
                enabled = false,
                onClick = { /*TODO*/ },
                text = "TextButtonApp"
            )
            BoxSpacer()
            TextButtonApp(
                enabled = true,
                onClick = { /*TODO*/ },
                text = "TextButtonApp"
            )
            BoxSpacer()
            IconButtonApp(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.AccountBox, null)
            }
            BoxSpacer()
            IconButtonApp(
                enabled = false,
                onClick = { /*TODO*/ }) {
                Icon(Icons.Default.AccountBox, null)
            }
            BoxSpacer()
        }

    }

}