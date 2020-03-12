package com.bsimsek.githubreposearch.core.data

import java.io.IOException

class EmptyApiResultException : IOException() {
    override val message: String
        get() = "Data is null"
}