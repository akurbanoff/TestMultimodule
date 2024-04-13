package ru.axas.core.base.extension

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Dimension
import coil.size.Size
import ru.axas.core.theme.LocalNavController
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import java.util.UUID
import kotlin.jvm.internal.ClassBasedDeclarationContainer
import kotlin.reflect.KClass


fun <T> modifyList(list: List<T>, value: T): List<T> {
    val listMut = list.toMutableList()
    if (listMut.contains(value)) {
        listMut.remove(value)
    } else {
        listMut.add(value)
    }
    return listMut
}


@Composable
inline fun <reified T : ViewModel> daggerViewModel(
    key: String? = null,
    crossinline viewModelInstanceCreator: () -> T
): T {
    val navController = LocalNavController.current
    val model = viewModel(
        modelClass = T::class.java,
        key = key,
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModelInstanceCreator() as T
            }
        }
    )
    return model
}


@Suppress("UPPER_BOUND_VIOLATED")
val <T> KClass<T>.getQualifiedName: String
    @JvmName("getJavaClass")
    get() = ((this as ClassBasedDeclarationContainer).jClass).enclosingClass?.kotlin?.qualifiedName
        ?: UUID.randomUUID().toString()


@Composable
inline fun <T> rememberDerivedState(crossinline producer: @DisallowComposableCalls () -> T) =
    remember { derivedStateOf { producer() } }

@Composable
inline fun <T> rememberDerivedState(
    key1: Any?,
    crossinline producer: @DisallowComposableCalls () -> T
) =
    remember(key1) { derivedStateOf { producer() } }

@Composable
inline fun <T> rememberDerivedState(
    key1: Any?,
    key2: Any?,
    crossinline producer: @DisallowComposableCalls () -> T
) =
    remember(key1, key2) { derivedStateOf { producer() } }


@Composable
inline fun <T> rememberState(crossinline producer: @DisallowComposableCalls () -> T) =
    remember { mutableStateOf(producer()) }

@Composable
inline fun <T> rememberState(key1: Any?, crossinline producer: @DisallowComposableCalls () -> T) =
    remember(key1) { mutableStateOf(producer()) }

@Composable
inline fun <T> rememberState(
    key1: Any?,
    key2: Any?,
    crossinline producer: @DisallowComposableCalls () -> T
) =
    remember(key1, key2) { mutableStateOf(producer()) }

@Composable
inline fun <T> rememberState(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    crossinline producer: @DisallowComposableCalls () -> T
) =
    remember(key1, key2, key3) { mutableStateOf(producer()) }

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    var countRestart by remember { mutableStateOf(0) }
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }
    LifeScreen(onResume = { countRestart++ })
    DisposableEffect(key1 = backPressedDispatcher, key2 = countRestart, effect = {
        backPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    })
}


@Composable
fun rememberImageRaw(@RawRes id: Int) = rememberAsyncImagePainter(
    model = ImageRequest.Builder(LocalContext.current)
        .apply {
            size(
                Size(
                    with(LocalDensity.current) {
                        LocalConfiguration.current.screenWidthDp.dp.roundToPx()
                    },
                    Dimension.Undefined
                )
            )
            decoderFactory(SvgDecoder.Factory())
            data(id)
            crossfade(false)
        }
        .build()
)


@Composable
fun LifeScreen(
    onCreate: () -> Unit = {},
    onStart: () -> Unit = {},
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
    onDestroy: () -> Unit = {},
    onAny: () -> Unit = {},
) {
    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> onCreate()
            Lifecycle.Event.ON_START -> onStart()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_PAUSE -> onPause()
            Lifecycle.Event.ON_STOP -> onStop()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
            Lifecycle.Event.ON_ANY -> onAny()
        }
    }
}


@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}


fun readJsonFromRaw(context: Context, @RawRes resourceId: Int): String {
    val inputStream = context.resources.openRawResource(resourceId)
    val json = try {
        inputStream.bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    } finally {
        inputStream.close()
    }
    return json
}

fun onBackspaceKeyEvent(event: KeyEvent, focusManager: FocusManager): Boolean {
    if (event.key != Key.Backspace) {
        focusManager.moveFocus(FocusDirection.Right)
    } else {
        focusManager.moveFocus(FocusDirection.Left)
    }
    return true
}

inline fun <T : Any> LazyListScope.itemsPaging(
    items: LazyPagingItems<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) = items(
    count = items.itemCount,
    key = if (key != null) items.itemKey {
        key(it)
    } else null
) {
    val item = items[it] ?: return@items
    itemContent(item)
}

inline fun <T : Any> LazyGridScope.itemsPaging(
    items: LazyPagingItems<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit
) = items(
    count = items.itemCount,
    key = if (key != null) items.itemKey {
        key(it)
    } else null
) {
    val item = items[it] ?: return@items
    itemContent(item)
}

fun formatterThousand(n: Long) =
    DecimalFormat("#,###", DecimalFormatSymbols(Locale.GERMANY)).format(n).replace(".", " ")