package ru.axas.core.base.compose_items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Dimension
import coil.size.Size
import kotlinx.coroutines.Dispatchers
import ru.axas.core.R
import ru.axas.core.theme.ThemeApp


//@Composable
//fun BoxImageLoad(
//    modifier: Modifier = Modifier,
//    image: Any?,
//    alignment: Alignment = Alignment.Center,
//    sizeToIntrinsics: Boolean = true,
//    contentScale: ContentScale = ContentScale.Fit,
//    alpha: Float = DefaultAlpha,
//    colorFilter: ColorFilter? = null,
//    @DrawableRes drawableError: Int? = R.drawable.hide_image,
//    @DrawableRes drawablePlaceholder: Int? = null,
//    colorLoader: Color = ThemeApp.colors.primary,
//    strokeWidthLoader: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
//    modifierOnImage: Modifier = Modifier,
//    content: @Composable BoxScope.(error: Boolean) -> Unit = {},
//) {
//
//    val des = LocalDensity.current
//    var sizeLoader by remember { mutableStateOf(0.dp) }
//    var isLoading by remember { mutableStateOf(false) }
//    var isError by remember { mutableStateOf(false) }
//
//    val painter = rememberAsyncImagePainter(onLoading = {
//        isLoading = true
//        isError = false
//    }, onError = {
//        isLoading = false
//        isError = true
//    }, onSuccess = {
//        isLoading = false
//        isError = false
//    }, model = ImageRequest.Builder(LocalContext.current).apply {
//        size(Size.ORIGINAL)
////        scale(Scale.FIT)
//        drawableError?.let { error(it) }
//        drawablePlaceholder?.let { placeholder(it) }
//        decoderFactory(SvgDecoder.Factory())
//        data(image ?: R.drawable.ic_person_default)
//        crossfade(true)
//    }.build())
//
//    Box(modifier = modifier
//        .onGloballyPositioned {
//            sizeLoader = with(des) {
//                (it.size.height / 2)
//                    .toDp()
//                    .coerceIn(25.dp, 40.dp)
//            }
//        }
//        .paint(
//            painter = painter,
//            alignment = alignment,
//            sizeToIntrinsics = sizeToIntrinsics,
//            contentScale = contentScale,
//            colorFilter = colorFilter,
//            alpha = alpha
//        )
//        .then(modifierOnImage)) {
//        content.invoke(this, isError)
//        if (isLoading) CircularProgressIndicator(
//            modifier = Modifier
//                .align(Alignment.Center)
//                .size(sizeLoader),
//            color = colorLoader,
//            strokeWidth = strokeWidthLoader,
//        )
//    }
//}

@Composable
fun BoxImageLoad(
    modifier: Modifier = Modifier,
    image: Any?,
    alignment: Alignment = Alignment.Center,
    sizeToIntrinsics: Boolean = true,
    crossfadeEnable: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    @DrawableRes drawablePlaceholder: Int? = R.drawable.hide_image,
    colorLoader: Color = ThemeApp.colors.primary,
    strokeWidthLoader: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    modifierImage: Modifier = Modifier,
    modifierOnImage: Modifier = Modifier,
    content: (@Composable BoxScope.(error: Boolean) -> Unit)? = null,
) {

    val isWebSVG = remember(image) {
        val isWebSVG = image.toString()
        isWebSVG.isNotEmpty() && isWebSVG.length > 3 && isWebSVG.takeLast(3).equals("svg", true)
    }

    val placeholderPaint by rememberUpdatedState(newValue = drawablePlaceholder?.let {
        painterResource(id = it)
    })

    val configuration by rememberUpdatedState(LocalConfiguration.current)
    val density by rememberUpdatedState(LocalDensity.current)
    val context by rememberUpdatedState(LocalContext.current)
    var sizeLoader by remember { mutableStateOf(0.dp) }

    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    val model = remember(image) {
        ImageRequest.Builder(context).apply {
            dispatcher(Dispatchers.IO)
            networkCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
            diskCachePolicy(CachePolicy.ENABLED)
            placeholderMemoryCacheKey(image.toString())
            diskCacheKey(image.toString())
            decoderFactory(SvgDecoder.Factory())
            data(image)
            size(
                if (isWebSVG) {
                    Size(
                        with(density) { configuration.screenWidthDp.dp.roundToPx() },
                        Dimension.Undefined
                    )
                } else {
                    Size(1920, Dimension.Undefined)
                }
            )
            crossfade(crossfadeEnable)
        }.build()
    }

    val painter = rememberAsyncImagePainter(
        model = model,
        contentScale = contentScale,
        onLoading = {
            isLoading = true
            isError = false
            isSuccess = false
        },
        onError = {
            isLoading = false
            isError = true
            isSuccess = true
        },
        onSuccess = {
            isLoading = false
            isError = false
            isSuccess = true
        })

    Box(
        modifier = modifier
            .onGloballyPositioned {
                sizeLoader = with(density) {
                    (it.size.height / 2)
                        .toDp()
                        .coerceIn(
                            25.dp,
                            40.dp
                        )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        placeholderPaint?.let { paint ->
            if (isLoading || isError) {
                Box(
                    modifier = Modifier
                        .then(modifierImage)
                        .paint(
                            painter = paint,
                            contentScale = ContentScale.Fit,
                            sizeToIntrinsics = false
                        )
                )
            }
        }
        Box(
            modifier = Modifier
                .then(modifierImage)
                .paint(
                    painter = painter,
                    alignment = alignment,
                    sizeToIntrinsics = sizeToIntrinsics,
                    contentScale = contentScale,
                    colorFilter = colorFilter,
                    alpha = alpha
                )
                .then(modifierOnImage)
        )

        content?.invoke(this, isError)

        if (isLoading) CircularProgressIndicator(
            modifier = Modifier
                .size(sizeLoader),
            color = colorLoader,
            strokeWidth = strokeWidthLoader,
        )
    }
}