package com.bsimsek.githubreposearch.data.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class RequestInterceptor @Inject constructor(private val context: Context): Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (!connectivityManager.isActiveNetworkMetered) {
            throw NoInternetException()
        }
        return chain.proceed(chain.request().newBuilder().build())
    }
}