package com.bsimsek.githubreposearch.data.network

sealed class DataHolder<out T : Any> {
    data class Success<out T : Any>(val data: T) : DataHolder<T>()
    data class Fail(val e: Exception) : DataHolder<Nothing>()
    data class NoInternetConnection(val exception: NoInternetException) : DataHolder<Nothing>()
}