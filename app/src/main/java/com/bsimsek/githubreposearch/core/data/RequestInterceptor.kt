package com.bsimsek.githubreposearch.core.data

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestInterceptor @Inject constructor(private val context: Context): Interceptor{
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isNetworkActive()) {
            throw NoInternetException()
        }
        return chain.proceed(chain.request().newBuilder().build())
    }
}