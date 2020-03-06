package com.bsimsek.githubreposearch.presentation.base

sealed class BaseUiState {
    class loading(): BaseUiState()
    class hasData<T: Any>(val data: T): BaseUiState()
    class errorState(val errorMessage: String): BaseUiState()
}