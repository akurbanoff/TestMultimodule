package ru.axas.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    private val CONNECT_TIMEOUT = 10L
    private val READ_TIMEOUT = 10L
    private val WRITE_TIMEOUT = 10L
//    private val IS_DEBUG = BuildConfig.DEBUG

    val BASE_URL = "http://80.90.187.107:81"

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(ApiLogger).apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        //requestInterceptor: RequestInterceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        //addInterceptor(requestInterceptor)
        addInterceptor(httpLoggingInterceptor)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
    }.build()
}

val ApiLogger = object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val logName = "ApiLogger"
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson = GsonBuilder().serializeNulls().setPrettyPrinting()
                    .create().toJson(JsonParser().parse(message))
                Log.i(logName, prettyPrintJson)
            } catch (m: JsonSyntaxException) {
                Log.i(logName, message)
            }
        } else {
            Log.i(logName, message)
            return
        }
    }
}