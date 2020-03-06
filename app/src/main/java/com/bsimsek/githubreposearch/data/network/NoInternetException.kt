package com.bsimsek.githubreposearch.data.network

import java.io.IOException

class NoInternetException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}