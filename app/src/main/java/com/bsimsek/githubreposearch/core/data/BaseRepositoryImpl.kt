package com.bsimsek.githubreposearch.core.data

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
                }?: DataHolder.Fail(ApiResultException())
            else
                DataHolder.Fail(IOException(response.toErrorMessage()))
        } catch (exception: IOException) {
            if (exception is NoInternetException)
                DataHolder.Fail(NoInternetException())
            else
                DataHolder.Fail(exception)
        }
    }
}