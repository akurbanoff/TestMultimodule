package ru.axas.core.base.compose_items

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.resolveDefaults
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.axas.core.base.extension.EmptyTextReplacement
import ru.axas.core.base.extension.ceilToIntPx
import ru.axas.core.theme.ThemeApp
import ru.axas.core.theme.TestMultimoduleTheme
import ru.axas.core.theme.res.DimApp
import kotlin.math.ceil

@Composable
fun DecoratedTextField(
    value: String,
    length: Int,
    modifier: Modifier = Modifier,
    boxWidth: Dp = 64.dp,
    boxHeight: Dp = 64.dp,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit,
) {
    val spaceBetweenBoxes = 8.dp
    BasicTextField(modifier = modifier,
        value = value,
        singleLine = true,
        onValueChange = {
            if (it.length <= length) {
                onValueChange(it)
            }
        },
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = {
            Row(
                Modifier.size(width = (boxWidth + spaceBetweenBoxes) * length, height = boxHeight),
                horizontalArrangement = Arrangement.spacedBy(spaceBetweenBoxes),
            ) {
                repeat(length) { index ->
                    Box(
                        modifier = Modifier
                            .size(boxWidth, boxHeight)
                            .border(
                                2.dp,
                                color = ThemeApp.colors.primary,
                                shape = RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = value.getOrNull(index)?.toString() ?: "",
                            textAlign = TextAlign.Center,
                            style = ThemeApp.typography.medium
                        )
                    }
                }
            }
        })
}

@Composable
fun OutlinedTextFieldOld(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = ThemeApp.typography.medium,
    placeholder: @Composable (() -> Unit)? = null,
    isPlaceholderCollapse: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLinesForeHeight: Int = 3,
    alignmentPlaceholder: Alignment = Alignment.CenterStart,
    alignmentTrailingIcon: Alignment = Alignment.CenterEnd,
    alignmentLeadingIcon: Alignment = Alignment.CenterStart,
    alignmentText: Alignment.Vertical = Alignment.CenterVertically,
    paddingVerticalForeText: Dp = 8.dp,
    paddingHorizontalForeText: Dp = 12.dp,
    textColor: Color = ThemeApp.colors.primary,
    borderColor: Color = ThemeApp.colors.primary.copy(alpha = .5f),
    // change 2 color
    noActiveColor: Color = ThemeApp.colors.primary,
    disabledColor: Color = ThemeApp.colors.primary,
    attentionColor: Color = ThemeApp.colors.attentionContent,
    placeholderContainerColor: Color = ThemeApp.colors.backgroundMain,
    containerColorCommon: Color = ThemeApp.colors.backgroundMain.copy(0F),
    shape: Shape = ThemeApp.shape.smallAll,
    containerColor: Color = containerColorCommon,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    paddingForePlaceholderTextInput: Dp = 20.dp,
    paddingForeTopTextPlaceHolder: Dp = 8.dp,
    sizeStrokeWidthLoaderHomeScreen: Dp = 2.dp,
) {
    val focused by interactionSource.collectIsFocusedAsState()
    val indicatorColorFinal = animateColorAsState(
        targetValue = if (isError) {
            attentionColor
        } else if (enabled && focused) {
            borderColor
        } else if (enabled) {
            noActiveColor
        } else {
            disabledColor
        }, animationSpec = tween(200)
    )

    val indicatorLineSize =
        if (focused || !enabled) {
            sizeStrokeWidthLoaderHomeScreen
        } else {
            DimApp.lineHeight
        }

    BasicTextField(value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle.merge(TextStyle(color = if (enabled) textColor else noActiveColor)),
        cursorBrush = SolidColor(if (isError) attentionColor else textColor),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->

            val localDensity = LocalDensity.current
            val leadingIconHeightDp = remember { mutableStateOf(0.dp) }
            val leadingIconWidthDp = remember { mutableStateOf(0.dp) }

            val trailingIconHeightDp = remember { mutableStateOf(0.dp) }
            val trailingIconWidthDp = remember { mutableStateOf(0.dp) }
            Box(modifier = Modifier.background(containerColorCommon)) {

                Column(modifier = Modifier.fillMaxWidth()) {

                    Box(modifier = Modifier.fillMaxWidth()) {

                        Row(
                            modifier = Modifier
                                .padding(top = paddingForeTopTextPlaceHolder)
                                .fillMaxWidth()
                                .background(containerColor, shape)
                                .border(
                                    width = indicatorLineSize,
                                    color = indicatorColorFinal.value,
                                    shape = shape
                                )
                                .minLinesHeight(
                                    minLines = minLinesForeHeight,
                                    textStyle = textStyle
                                )
                                .padding(
                                    start = leadingIconWidthDp.value,
                                    end = trailingIconWidthDp.value
                                )
                                .padding(
                                    vertical = paddingVerticalForeText,
                                    horizontal = paddingHorizontalForeText
                                ),
                            verticalAlignment = alignmentText,
                        ) {
                            innerTextField()
                        }


                        if (leadingIcon != null) {
                            Box(
                                modifier = Modifier
                                    .padding(
                                        top = paddingForeTopTextPlaceHolder,
                                        start = paddingForeTopTextPlaceHolder
                                    )
                                    .align(alignmentLeadingIcon)
                                    .onGloballyPositioned { coordinates ->
                                        leadingIconHeightDp.value =
                                            with(localDensity) { coordinates.size.height.toDp() }
                                        leadingIconWidthDp.value =
                                            with(localDensity) { coordinates.size.width.toDp() }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                leadingIcon()
                            }
                        }
                        if (trailingIcon != null && enabled) {
                            Box(
                                modifier = Modifier
                                    .padding(
                                        top = paddingForeTopTextPlaceHolder,
                                        end = paddingForeTopTextPlaceHolder
                                    )
                                    .align(alignmentTrailingIcon)
                                    .onGloballyPositioned { coordinates ->
                                        trailingIconHeightDp.value =
                                            with(localDensity) { coordinates.size.height.toDp() }
                                        trailingIconWidthDp.value =
                                            with(localDensity) { coordinates.size.width.toDp() }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                trailingIcon()
                            }
                        }

                        if (placeholder != null) {
                            if (value.text.isBlank()) {
                                val colorText = if (enabled) {
                                    noActiveColor
                                } else {
                                    disabledColor
                                }
                                Box(
                                    modifier = Modifier
                                        .align(alignmentPlaceholder)
                                        .padding(
                                            top = paddingForeTopTextPlaceHolder,
                                            start = leadingIconWidthDp.value,
                                            end = trailingIconWidthDp.value
                                        )
                                        .padding(
                                            vertical = paddingVerticalForeText,
                                            horizontal = paddingHorizontalForeText
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CompositionLocalProvider(
                                        LocalTextStyle provides textStyle.copy(
                                            color = colorText
                                        )
                                    ) {
                                        placeholder()
                                    }

                                }
                            } else if (isPlaceholderCollapse) {
                                val colorText = if (enabled) {
                                    indicatorColorFinal.value
                                } else {
                                    noActiveColor
                                }
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = paddingForePlaceholderTextInput)
                                        .background(placeholderContainerColor)
                                        .align(Alignment.TopStart),
                                    contentAlignment = Alignment.TopStart
                                ) {
                                    CompositionLocalProvider(
                                        LocalTextStyle provides textStyle.copy(
                                            fontSize = 10.sp,
                                            color = colorText
                                        )
                                    ) {
                                        placeholder()
                                    }
                                }
                            }
                        }
                    }

                    if (supportingText != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = paddingHorizontalForeText),
                            verticalAlignment = alignmentText,
                        ) {
                            CompositionLocalProvider(
                                LocalTextStyle provides textStyle.copy(
                                    fontSize = 12.sp,
                                    color = indicatorColorFinal.value
                                ),
                            ) {
                                supportingText()
                            }
                        }
                    }
                }
            }
        })
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.minLinesHeight(
    minLines: Int,
    textStyle: TextStyle
) = composed {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val resolvedStyle = remember(textStyle, layoutDirection) {
        resolveDefaults(textStyle, layoutDirection)
    }
    val resourceLoader = LocalFontFamilyResolver.current

    val heightOfTextLines = remember(
        density,
        textStyle,
        layoutDirection
    ) {
        val lines = (EmptyTextReplacement + "\n").repeat(minLines - 1)
        computeSizeForDefaultText(
            style = resolvedStyle,
            density = density,
            text = lines,
            maxLines = minLines,
            resourceLoader
        ).height
    }
    val heightInDp: Dp = with(density) { heightOfTextLines.toDp() }
    Modifier.defaultMinSize(minHeight = heightInDp)
}

fun computeSizeForDefaultText(
    style: TextStyle,
    density: Density,
    text: String = EmptyTextReplacement,
    maxLines: Int = 1,
    fontFamilyResolver: FontFamily.Resolver
): IntSize {
    val paragraph = Paragraph(
        paragraphIntrinsics = ParagraphIntrinsics(
            text = text,
            style = style,
            density = density,
            fontFamilyResolver = fontFamilyResolver
        ),
        maxLines = maxLines,
        constraints = Constraints(maxWidth = ceil(Float.POSITIVE_INFINITY).toInt()),
    )

    return IntSize(paragraph.minIntrinsicWidth.ceilToIntPx(), paragraph.height.ceilToIntPx())
}

@Composable
fun TextFieldApp(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = ThemeApp.typography.medium,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = 5,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isIndicatorOn: Boolean = false,
    colors: TextFieldColors = textColorField(isIndicatorOn),
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.clip(ThemeApp.shape.smallAll),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText
    )
}

@Composable
fun TextFieldAppStr(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = ThemeApp.typography.medium,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isIndicatorOn: Boolean = true,
    colors: TextFieldColors = textColorField(isIndicatorOn),
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText
    )
}

@Composable
fun TextFieldOutlinesAppStr(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = ThemeApp.typography.medium,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = shapeBoard(),
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isIndicatorOn: Boolean = true,
    colors: TextFieldColors = textColorField(isIndicatorOn),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText
    )
}

@Composable
fun TextFieldOutlinesApp(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = ThemeApp.typography.medium,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = shapeBoard(),
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isIndicatorOn: Boolean = true,
    colors: TextFieldColors = textColorField(isIndicatorOn),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText
    )
}

@Composable
fun CustomTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "",
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    BasicTextField(modifier = modifier
        .background(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.shapes.small,
        )
        .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = fontSize
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Row(
                modifier.heightIn(min = 40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (value.text.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            fontSize = fontSize
                        )
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}


@Composable
private fun shapeBoard() = ThemeApp.shape.smallAll

@Composable
private fun textColorField(isIndicatorOn: Boolean = true) = TextFieldDefaults.colors(

    focusedTextColor = ThemeApp.colors.black,
    unfocusedTextColor = ThemeApp.colors.black,
    disabledTextColor = ThemeApp.colors.slateGrey,
    errorTextColor = ThemeApp.colors.black,

    focusedLabelColor = ThemeApp.colors.black,
    unfocusedLabelColor = ThemeApp.colors.slateGrey,
    disabledLabelColor = ThemeApp.colors.slateGrey,
    errorLabelColor = ThemeApp.colors.error,

    focusedPlaceholderColor = ThemeApp.colors.slateGrey,
    unfocusedPlaceholderColor = ThemeApp.colors.slateGrey,
    disabledPlaceholderColor = ThemeApp.colors.slateGrey,
    errorPlaceholderColor = ThemeApp.colors.error,

    focusedIndicatorColor = ThemeApp.colors.primary.copy(alpha = if (isIndicatorOn) 1f else 0f),
    unfocusedIndicatorColor = ThemeApp.colors.primary.copy(alpha = if (isIndicatorOn) 1f else 0f),
    disabledIndicatorColor = ThemeApp.colors.slateGrey.copy(alpha = if (isIndicatorOn) 1f else 0f),
    errorIndicatorColor = ThemeApp.colors.error.copy(alpha = if (isIndicatorOn) 1f else 0f),

    focusedPrefixColor = ThemeApp.colors.black,
    unfocusedPrefixColor = ThemeApp.colors.black,
    disabledPrefixColor = ThemeApp.colors.slateGrey,
    errorPrefixColor = ThemeApp.colors.black,

    focusedSuffixColor = ThemeApp.colors.black,
    unfocusedSuffixColor = ThemeApp.colors.black,
    disabledSuffixColor = ThemeApp.colors.slateGrey,
    errorSuffixColor = ThemeApp.colors.black,

    cursorColor = ThemeApp.colors.primary.copy(alpha = .5f),
    errorCursorColor = ThemeApp.colors.error,

    selectionColors = TextSelectionColors(
        handleColor = ThemeApp.colors.primary,
        backgroundColor = ThemeApp.colors.primary.copy(alpha = 0.4f)
    ),

    focusedLeadingIconColor = ThemeApp.colors.primary,
    unfocusedLeadingIconColor = ThemeApp.colors.slateGrey,
    disabledLeadingIconColor = ThemeApp.colors.slateGrey,
    errorLeadingIconColor = ThemeApp.colors.primary,

    focusedTrailingIconColor = ThemeApp.colors.primary,
    unfocusedTrailingIconColor = ThemeApp.colors.slateGrey,
    disabledTrailingIconColor = ThemeApp.colors.slateGrey,
    errorTrailingIconColor = ThemeApp.colors.error,

    focusedSupportingTextColor = ThemeApp.colors.primary,
    unfocusedSupportingTextColor = ThemeApp.colors.slateGrey,
    disabledSupportingTextColor = ThemeApp.colors.slateGrey,
    errorSupportingTextColor = ThemeApp.colors.error,

    errorContainerColor = ThemeApp.colors.white,
    focusedContainerColor = ThemeApp.colors.white,
    unfocusedContainerColor = ThemeApp.colors.white,
    disabledContainerColor = ThemeApp.colors.white,
)


@Preview
@Composable
private fun TestTextField() {
    TestMultimoduleTheme {
        var text1 by remember { mutableStateOf(TextFieldValue()) }
        var text2 by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .background(ThemeApp.colors.backgroundMain)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextFieldOutlinesAppStr(
                value = text2,
                onValueChange = { text2 = it },
                supportingText = {
                    Text(text = "Supporting Text")
                },
                label = {
                    Text(text = "Label Text")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text(text = "Place Holder Text")
                }
            )
            Box(modifier = Modifier.size(10.dp))
            TextFieldAppStr(
                value = text2,
                onValueChange = { text2 = it },
                supportingText = {
                    Text(text = "Supporting Text")
                },
                label = {
                    Text(text = "Label Text")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text(text = "Place Holder Text")
                }
            )
            Box(modifier = Modifier.size(10.dp))
            TextFieldOutlinesApp(
                value = text1,
                onValueChange = { text1 = it },
                supportingText = {
                    Text(text = "Supporting Text")
                },
                label = {
                    Text(text = "Label Text")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text(text = "Place Holder Text")
                }
            )
            Box(modifier = Modifier.size(10.dp))
            TextFieldApp(
                value = text1,
                onValueChange = { text1 = it },
                supportingText = {
                    Text(text = "Supporting Text")
                },
                label = {
                    Text(text = "Label Text")
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                },
                placeholder = {
                    Text(text = "Place Holder Text")
                }
            )
            Box(modifier = Modifier.size(10.dp))
        }
    }
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int,
    onOtpTextChange: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = ThemeApp.colors.white,
    contentColor: Color = ThemeApp.colors.primary,
    border: Dp = 1.dp,
    shape: Shape = ThemeApp.shape.mediumAll,
    indicatorUnFocusedColor: Color = ThemeApp.colors.primary,
    indicatorFocusedColor: Color = ThemeApp.colors.primary,
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text)
            }
        },
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Send,
            keyboardType = KeyboardType.NumberPassword
        ),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CodeFieldBoxChar(
                        index = index,
                        text = otpText,
                        background = background,
                        border = border,
                        shape = shape,
                        contentColor = contentColor,
                        indicatorUnFocusedColor = indicatorUnFocusedColor,
                        indicatorFocusedColor = indicatorFocusedColor,
                    )
                    Box(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

