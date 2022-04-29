package com.vignesh.todo.common.repository.network

import com.google.gson.GsonBuilder
import com.vignesh.todo.common.repository.local.TDSharePreferenceImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object LNApiManager {
    private const val TIMEOUT: Long = 30
    private const val APPLICATION_JSON = "application/json"
    private const val KEY_ACCEPT_TYPE = "Accept"
    private const val KEY_CONTENT_TYPE = "content-type"
    private const val AUTHORISATION_KEY = "Authorization"
    private const val REFRESH_TOKEN_KEY = "Refresh-token"
    fun generateOkHttpClient(
        isAuthTokenNeed: Boolean = false,
        isRefreshTokenNeed: Boolean = false,
        preference: TDSharePreferenceImpl? = null,
        authInterceptor: Interceptor? = null,
        additionalInterceptors: List<Interceptor>? = null,
    ) = OkHttpClient().newBuilder().apply {
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        if (isAuthTokenNeed) {
            authInterceptor?.let {
                addInterceptor(it)
            }
        }
        additionalInterceptors?.forEach {
            addInterceptor(it)
        }
        addInterceptor { chain ->
            chain.proceed(
                getRequest(
                    request = chain.request(),
                    isAuthTokenNeed = isAuthTokenNeed,
                    isRefreshTokenNeed = isRefreshTokenNeed,
                    preference = preference
                )
            )
        }
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }.build()

    fun <T> buildRetrofitClient(okHttpClient: OkHttpClient, client: Class<T>): Class<T> =
        Retrofit.Builder().apply {
            baseUrl("")
            client(okHttpClient)
            addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
        }.build().create(client::class.java)

    @Throws(IOException::class)
    fun getRequest(
        request: Request,
        isAuthTokenNeed: Boolean = false,
        isRefreshTokenNeed: Boolean = false,
        preference: TDSharePreferenceImpl?,
    ) = request.newBuilder().apply {
        request.headers[KEY_ACCEPT_TYPE] ?: kotlin.run {
            addHeader(KEY_ACCEPT_TYPE, APPLICATION_JSON)
        }
        request.headers[KEY_CONTENT_TYPE] ?: kotlin.run {
            addHeader(KEY_CONTENT_TYPE, APPLICATION_JSON)
        }
        preference?.let {
            if (isAuthTokenNeed) {
                removeHeader(AUTHORISATION_KEY)
                it.autherticationToken?.let { addHeader(AUTHORISATION_KEY, it) }
            }
            if (isRefreshTokenNeed) {
                removeHeader(REFRESH_TOKEN_KEY)
                it.refreshToken?.let { addHeader(REFRESH_TOKEN_KEY, it) }
            }
        }
    }.build()
}

sealed interface LNApiManagerImpl {

}