package com.bsimsek.githubreposearch.core.data

import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import javax.inject.Singleton

@Singleton
open class BaseRepositoryImpl {

    suspend fun <T : Any> handleApiCall(call: suspend () -> Call<T>): DataHolder<T>? {
        return when (val result = getApiResult(call)) {
            is DataHolder.Loading -> DataHolder.Loading
            is DataHolder.Success -> DataHolder.Success(result.data)
            is DataHolder.Fail -> DataHolder.Fail(result.error)
            is DataHolder.NoInternetConnection -> DataHolder.NoInternetConnection(NoInternetException())
        }
    }

    private suspend fun <T : Any> getApiResult(call: suspend () -> Call<T>): DataHolder<T> {
        return try {
            val response = call.invoke() as Response<T>
            if (response.isSuccessful)
                response.body()?.let {
                    DataHolder.Success(it)
                }?: DataHolder.Fail(ApiResultException().message)
            else
                DataHolder.Fail(response.toErrorMessage())
        } catch (exception: IOException) {
            if (exception is NoInternetException)
                DataHolder.NoInternetConnection(NoInternetException())
            else
                DataHolder.Fail(exception.message!!)
        }
    }
}