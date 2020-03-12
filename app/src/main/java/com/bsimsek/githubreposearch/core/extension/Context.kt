package com.bsimsek.githubreposearch.core.extension

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkActive(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo?.isConnected ?: false
}