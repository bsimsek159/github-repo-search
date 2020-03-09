package com.bsimsek.githubreposearch.core.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsimsek.githubreposearch.core.data.DataHolder

abstract class BaseViewModel<T : DataHolder<*>> : ViewModel() {
    protected var mUiState = MutableLiveData<T>()
    open val uiState: LiveData<T> = mUiState
}