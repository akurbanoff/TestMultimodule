package ru.axas.core.base.extension

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import ru.axas.core.network.ErrorApi
import ru.axas.core.network.RudApi

fun Any.objectToJson(): String = Gson().toJson(this)
inline fun <reified T> String.jsonToObject(): T? = try {
    Gson().fromJson(this, T::class.java) ?: null
} catch (e: Exception) {
    e.printStackTrace()
    null
}


fun <T> stringToArray(s: String?, clazz: Class<Array<T>>?): List<T>? = try {
    val arr = Gson().fromJson(s, clazz)
    arr.toList()
} catch (e: Exception) {
    e.printStackTrace()
    null
}


fun <T> Response<T>.errorBodyToMap(): RudApi<Any> {
    val str  = "UNKNOWN ERROR"
    val responseError = this.errorBody()?.string()?: str
    return responseError.jsonToObject() ?: RudApi(
        errors = listOf(ErrorApi( code = 0, message = str)),
        message = str)
}


fun <T> Response<T>.toMapResponseData(): RudApi<T> {
    val responseError = this.errorBody()?.string() ?: "UNKNOWN ERROR"
    return responseError.jsonToObject() ?: RudApi(
        data = null,
        description = responseError,
        errors = listOf(
            ErrorApi(
            additional = null,
            code = 508,
            message = responseError,
            path = null
        )
        ),
        message = responseError,
        paginator = null
    )
}

fun <T> Response<RudApi<T>>.errorBodyParsing(): RudApi<T>? {
    return this.errorBody()?.string()?.jsonToObject<RudApi<T>>()
}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun ResponseBody.stringSuspending() = withContext(Dispatchers.IO) { string() }