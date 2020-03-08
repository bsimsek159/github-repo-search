package com.bsimsek.githubreposearch.data.network

sealed class DataHolder {
    object Loading : DataHolder()
    data class Success<out T : Any>(val data: T) : DataHolder()
    data class Fail(val e: Exception) : DataHolder()
    data class NoInternetConnection(val exception: NoInternetException) : DataHolder()
}