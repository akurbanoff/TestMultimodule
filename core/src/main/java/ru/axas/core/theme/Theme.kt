package ru.axas.core.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.axas.core.base.extension.rememberState
import ru.axas.core.theme.res.ColorApp
import ru.axas.core.theme.res.ShapesApp
import ru.axas.core.theme.res.TypographyApp

@Composable
fun TestMultimoduleTheme(
    colors: ColorScheme = ColorApp.LightPalette,
    typography: TypographyScheme = TypographyApp.Typography,
    shape: ShapeScheme = ShapesApp.Shapes,
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {
//    var globalData by rememberState { GlobalDada.value ?: DataSingleLive() }
//    GlobalDataObserver { globalData = it }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    CompositionLocalProvider(
        LocalColorsAxas provides colors,
        LocalTypographyAxas provides typography,
        LocalShapeAxas provides shape,
//        LocalGlobalData provides globalData,
        LocalNavController provides navController,
        content = content
    )
}

val LocalNavController =
    staticCompositionLocalOf<NavHostController> { error("no NavHostController provided!") }