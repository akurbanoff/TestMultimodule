package ru.axas.core.base.extension

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.resolveDefaults
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlin.math.ceil
import kotlin.math.roundToInt

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

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableNoRipple(
    onClick: () -> Unit
) = composed {
    clickable(
        onClick = onClick,
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    )
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

internal const val DefaultWidthCharCount = 5
internal val EmptyTextReplacement = "H".repeat(DefaultWidthCharCount)
internal fun Float.ceilToIntPx(): Int = ceil(this).roundToInt()


@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableRipple(onClick: () -> Unit) = composed {
    clickable(
        onClick = onClick,
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple()
    )
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableRipple(enabled: Boolean = true, onClick: () -> Unit) = composed {
    clickable(
        onClick = onClick,
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(),
        enabled = enabled
    )
}


@OptIn(FlowPreview::class)
@Composable
fun <T> multipleEventsCutter(
    content: @Composable (MultipleEventsCutterManager) -> T
): T {
    val debounceState = remember {
        MutableSharedFlow<() -> Unit>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    val result = content(
        object : MultipleEventsCutterManager {
            override fun processEvent(event: () -> Unit) {
                debounceState.tryEmit(event)
            }
        }
    )

    LaunchedEffect(key1 = true) {
        debounceState.debounce(300L)
            .collect { onClick ->
                onClick.invoke()
            }
    }
    return result
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    multipleEventsCutter { manager ->
        Modifier.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { manager.processEvent { onClick() } },
            role = role,
            indication = rememberRipple(),
            interactionSource = remember { MutableInteractionSource() }
        )
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.minimumTouchTargetSizeApp(
    size: DpSize = DpSize(48.dp, 48.dp)
): Modifier = composed {
    MinimumTouchTargetModifier(size)
}

private class MinimumTouchTargetModifier(val size: DpSize) : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {

        val placeable = measurable.measure(constraints)

        // Be at least as big as the minimum dimension in both dimensions
        val width = maxOf(placeable.width, size.width.roundToPx())
        val height = maxOf(placeable.height, size.height.roundToPx())

        return layout(width, height) {
            val centerX = ((width - placeable.width) / 2f).roundToInt()
            val centerY = ((height - placeable.height) / 2f).roundToInt()
            placeable.place(centerX, centerY)
        }
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? MinimumTouchTargetModifier ?: return false
        return size == otherModifier.size
    }

    override fun hashCode(): Int {
        return size.hashCode()
    }
}

interface MultipleEventsCutterManager {
    fun processEvent(event: () -> Unit)

    companion object
}

fun MultipleEventsCutterManager.Companion.get(): MultipleEventsCutterManager=
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl: MultipleEventsCutterManager {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= 300L)
            event.invoke()
        lastEventTimeMs = now
    }
}