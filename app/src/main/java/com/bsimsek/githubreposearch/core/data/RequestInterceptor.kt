package com.bsimsek.githubreposearch.core.data

import android.content.Context
import com.bsimsek.githubreposearch.core.extension.isNetworkActive
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestInterceptor @Inject constructor(private val context: Context) : Interceptor {

    companion object {
        private const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isNetworkActive()) {
            throw NoInternetException()
        }
        return chain.proceed(
            chain.request()
                .newBuilder()
                .header(HEADER_ACCEPT_LANGUAGE, Locale.getDefault().language)
                .build()
        )
    }
}