package ru.axas.core.network

import android.util.Log
import ru.axas.core.theme.res.TextApp

data class RudApi<T>(
    val data: T? = null,
    val description: String? = null,
    val errors: List<ErrorApi>? = null,
    val message: String? = null,
    val paginator: PaginationApi? = null,
) {

    companion object {
        fun <T> getError(
            description: String? = null,
            errors: List<ErrorApi>? = null,
            errorCode: Int? = null
        ): RudApi<T> {
            val str = TextApp.textSomethingWentWrong
            return RudApi(
                data = null,
                description = description ?: str,
                errors = if (errors.isNullOrEmpty()) listOf(ErrorApi(
                    message = description ?: str,
                    code = errorCode ?: 508
                )) else errors,
                message = null,
                paginator = null
            )
        }
    }
    fun formattedErrors(): String {
        Log.e("formattedErrors", errors?.toList().toString())
        var errors = ""
        this.errors?.forEach { error ->
            errors += "${error.message.toString()}\n"
        }
        return errors.trim()
    }

    fun getCodeError(): Int {
        if (this.data != null) return 200
        val firstError = this.errors?.firstOrNull()
        return firstError?.code ?: 508
    }
    fun getCodeResponse(): Int {
        if (this.data != null) return 200
        val firstError = this.errors?.firstOrNull()
        return firstError?.code ?: 508
    }

    fun  getDescriptionRudApi() = if (this.description.isNullOrBlank()) {
        TextApp.textSomethingWentWrong
    } else {
        this.description.take(150)
    }
}

data class ErrorApi(
    val additional: String? = null,
    val code: Int? = null,
    val message: String? = null,
    val path: String? = null
)

data class MetaApi(
    val paginator: PaginationApi? = null
)

data class PaginationApi(
    val has_next: Boolean? = null,
    val has_prev: Boolean? = null,
    val page: Int? = null,
    val total: Int? = null
)

