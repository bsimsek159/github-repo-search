package com.bsimsek.githubreposearch.presentation.base

sealed class BaseUiState {
    class Loading: BaseUiState()
    class Success<T: Any>(val data: T): BaseUiState()
    class Fail(val errorMessage: String): BaseUiState()
}