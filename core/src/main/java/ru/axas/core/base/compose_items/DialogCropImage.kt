package ru.axas.core.base.compose_items

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.net.toUri
import kotlinx.coroutines.delay
import ru.axas.core.R
import ru.axas.core.base.compose_items.crop.AspectRatio
import ru.axas.core.base.compose_items.crop.CropImage
import ru.axas.core.base.compose_items.crop.CropImageOption
import ru.axas.core.base.compose_items.crop.rememberCropifyState
import ru.axas.core.base.extension.createImageFile
import ru.axas.core.base.extension.rememberImageRaw
import ru.axas.core.base.extension.rememberState
import ru.axas.core.theme.ThemeApp
import ru.axas.core.theme.res.DimApp

@Composable
fun DialogCropImage(
    onDismiss: () -> Unit,
    uriImage: Uri,
    onImageCroppedUri: (Uri) -> Unit,
    gridColor: Color = ThemeApp.colors.primary,
    scrimColor: Color = Color.Black,
) {
    val context by rememberUpdatedState(LocalContext.current)
    var isStart by rememberState { false }
    var onRotate by rememberState { 0 }
    val coreDismiss = remember { { isStart = false } }
    LaunchedEffect(Unit) { isStart = true }

    val crop = rememberCropifyState()
    val getUriNewImage: (ImageBitmap) -> Uri = remember {
        { image ->
            val fillNamePatch = context.createImageFile()
            val bitmap = image.asAndroidBitmap()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fillNamePatch.outputStream())
            fillNamePatch.toUri()
        }
    }
    var targetValueBackground by rememberState { .5f }
    val alignBackground = animateFloatAsState(
        targetValue = .5f,
        animationSpec = tween(SpiderCollapsing / 2), label = ""
    )

    val cropOption by rememberState(alignBackground) {
        CropImageOption().copy(
            frameAspectRatio = AspectRatio(1, 1),
            gridColor = gridColor,
            frameColor = gridColor,
            frameAlpha = alignBackground.value,
            gridAlpha = alignBackground.value,
        )
    }

    LaunchedEffect(isStart) {
        if (!isStart) {
            targetValueBackground = 0f
            delay((SpiderCollapsing * 0.8).toLong())
            onDismiss.invoke()
        }
    }

    Popup(
        alignment = Alignment.Center,
        offset = IntOffset(0, 0),
        onDismissRequest = coreDismiss,
        properties = PopupProperties(focusable = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(scrimColor.copy(alignBackground.value))
        ) {
            AnimatedVisibility(
                modifier = Modifier.fillMaxSize(),
                visible = isStart,
                enter = EnterDialog,
                exit = ExitDialog
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    CropImage(
                        modifier = Modifier.fillMaxSize(),
                        uri = uriImage,
                        state = crop,
                        onRotate = onRotate,
                        option = cropOption,
                        onImageCropped = {
                            onImageCroppedUri.invoke(getUriNewImage(it))
                            coreDismiss.invoke()
                        },
                        onFailedToLoadImage = {
                            coreDismiss.invoke()
                        },
                    )



                    Row(
                        modifier = Modifier
                            .padding(DimApp.screenPadding)
                            .align(Alignment.BottomEnd),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButtonApp(
                            onClick = { onRotate += 1 }
                        ) {
                            IconApp(
                                painter = rememberImageRaw(id = R.raw.ic_cached),
                                tint = ThemeApp.colors.primary
                            )
                        }
                        BoxSpacer(.5f)
                        IconButtonApp(
                            onClick = crop::crop
                        ) {
                            IconApp(
                                painter = rememberImageRaw(id = R.raw.ic_send),
                                tint = ThemeApp.colors.primary
                            )
                        }
                    }

                }
            }
        }
    }
}

private const val SpiderCollapsing = 1000
private val EnterDialog = fadeIn(animationSpec = tween(SpiderCollapsing))
private val ExitDialog = fadeOut(animationSpec = tween(SpiderCollapsing))

