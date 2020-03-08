package com.bsimsek.githubreposearch.data.network

import java.io.IOException

class EmptySearchResultException : IOException() {
    override val message: String
        get() = "No matched repository"
}