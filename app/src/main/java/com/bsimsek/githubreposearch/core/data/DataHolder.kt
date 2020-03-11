package com.bsimsek.githubreposearch.core.data


sealed class DataHolder<out T : Any> {
    object Loading : DataHolder<Nothing>()
    data class Success<out T : Any>(val data: T?) : DataHolder<T>()
    data class Fail(val error: String) : DataHolder<Nothing>()
    data class NoInternetConnection(val exception: NoInternetException) : DataHolder<Nothing>()
}