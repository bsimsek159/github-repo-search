package com.bsimsek.githubreposearch.data.repo

import android.util.Log
import com.bsimsek.githubreposearch.data.network.DataHolder
import com.bsimsek.githubreposearch.data.network.NoInternetException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

open class BaseRepositoryImpl {

    suspend fun <T : Any> handleApiCall(call: suspend () -> Response<T>, errorContext: String): T? {

        val result: DataHolder<T> = getApiResult(call)
        var data: T? = null

        when (result) {
            is DataHolder.Success -> data = result.data
            is DataHolder.Fail -> Log.e("Error", "${result.e}")
            is DataHolder.NoInternetConnection -> Log.e("NoInternetConnection", "No Internet Connection")
        }

        return data
    }

    private suspend fun <T : Any> getApiResult(call: suspend () -> Response<T>): DataHolder<T> {
        val result: DataHolder<T>
        result = try {
            val response = call.invoke()
            if (response.isSuccessful)
                DataHolder.Success(response.body()!!)
            else
                DataHolder.Fail(IOException(setErrorMessage(response)))
        } catch (exception: IOException) {
            if (exception is NoInternetException)
                DataHolder.Fail(exception)
            else
                DataHolder.Fail(exception)
        }
        return result
    }

    private fun <T : Any> setErrorMessage(response: Response<T>): String {
        val code = response.code().toString()
        val message = try {
            val jObjError = JSONObject(response.errorBody()?.string())
            jObjError.getJSONObject("error").getString("message")
        } catch (e: Exception) {
            e.message
        }
        return if (message.isNullOrEmpty()) " error code = $code " else " error code = $code  & error message = $message "
    }
}