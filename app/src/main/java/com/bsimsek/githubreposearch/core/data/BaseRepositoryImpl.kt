package com.bsimsek.githubreposearch.core.data

import com.bsimsek.githubreposearch.core.extension.toErrorMessage
import retrofit2.Response
import java.io.IOException
import javax.inject.Singleton

@Singleton
open class BaseRepositoryImpl {

    suspend fun <T : Any> handleApiCall(call: suspend () -> Response<T?>): DataHolder<T>? {
        return when (val result = getApiResult(call)) {
            is DataHolder.Loading -> DataHolder.Loading
            is DataHolder.Success -> DataHolder.Success(result.data)
            is DataHolder.Fail -> DataHolder.Fail(result.e)
            is DataHolder.NoInternetConnection -> DataHolder.Fail(NoInternetException())
        }
    }

    private suspend fun <T : Any> getApiResult(call: suspend () -> Response<T?>): DataHolder<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful)
                response.body()?.let {
                    DataHolder.Success(it)
                } ?: DataHolder.Fail(EmptyApiResultException())
            else
                DataHolder.Fail(IOException(response.toErrorMessage()))
        } catch (exception: IOException) {
            DataHolder.Fail(if (exception is NoInternetException) NoInternetException() else exception)
        }
    }
}