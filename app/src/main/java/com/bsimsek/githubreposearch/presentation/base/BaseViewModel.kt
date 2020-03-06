package com.bsimsek.githubreposearch.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T : BaseUiState> : ViewModel() {
    protected var mUiState = MutableLiveData<T>()
    open val uiState: LiveData<T> = mUiState

}