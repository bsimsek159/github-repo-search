package com.bsimsek.githubreposearch.core.extension

import org.json.JSONObject
import retrofit2.Response

fun Response<*>.toErrorMessage(): String {
    val code = this.code().toString()
    val message = try {
        val jObjError = JSONObject(this.errorBody()?.string() ?: "empty body")
        jObjError.getJSONObject("error").getString("message")
    } catch (e: Exception) {
        e.message
    }
    return if (message.isNullOrEmpty()) " error code = $code " else " error code = $code  & error message = $message "
}