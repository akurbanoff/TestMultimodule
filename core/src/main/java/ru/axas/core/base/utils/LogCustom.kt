package ru.axas.core.base.utils

import android.util.Log

private const val mainStr = "__ ru.axas.testmultimodule"
private val SHOW_LOG = true

fun logI(vararg any: Any?) {
    if (!SHOW_LOG) return
    Log.i(mainStr, any.toList().toString())
}

fun logE(vararg any: Any?) {
    if (!SHOW_LOG) return
    Log.e(mainStr, any.toList().toString())
}

fun logD(vararg any: Any?) {
    if (!SHOW_LOG) return
    Log.d(mainStr, any.toList().toString())
}

fun logW(vararg any: Any?) {
    if (!SHOW_LOG) return
    Log.w(mainStr, any.toList().toString())
}

fun logV(vararg any: Any?) {
    if (!SHOW_LOG) return
    Log.v(mainStr, any.toList().toString())
}

fun logDRRealise(vararg any: Any?) {
    Log.d(mainStr, any.toList().toString())
}

fun logIRRealise(vararg any: Any?) {
    Log.i(mainStr, any.toList().toString())
}