package ru.axas.core.base.utils

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable

interface ScreenBase {

    val rote: String

    fun NavGraphBuilder.addRoute(
        enterTransition: (
        @JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        )? = null,
        exitTransition: (
        @JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        )? = null,
        popEnterTransition: (
        @JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        )? = enterTransition,
        popExitTransition: (
        @JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        )? = exitTransition
    ) = this.composable(
        route = rote,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) { entry ->
        Content(entry = entry)
    }

    @Composable
    fun AnimatedContentScope.Content(entry: NavBackStackEntry)
}

fun NavGraphBuilder.addScreen(screen: ScreenBase) = with(screen) { addRoute() }

open class RouteScreen(private val keyScreen: String) {

    private fun String.setNameArguments(vararg string: String): String {
        if (string.isEmpty()) return this
        var stringConcat = "$this?"
        string.forEach { s -> stringConcat += "$s={$s}," }
        return stringConcat.dropLast(1)
    }

    open val listArguments: List<String> = listOf()

    val roteKey by lazy { keyScreen.setNameArguments(*listArguments.toTypedArray()) }

    private fun roteWithArg(arg: Map<String, Any?>) = buildString {
        append("${keyScreen}?")
        arg.forEach { (keyArg, valArg) ->
            append("${keyArg}=")
            append("$valArg,")
        }
    }.dropLast(1)

    fun rote(
        host: NavHostController,
        arg: Map<String, Any?> = mapOf(),
        builder: (NavOptionsBuilder.() -> Unit)? = null
    ) {
        val valueRote = if (arg.isEmpty()) roteKey else roteWithArg(arg)
        Log.d("NavArguments", valueRote)
        host.navigate(valueRote){builder?.invoke(this)}
    }
}