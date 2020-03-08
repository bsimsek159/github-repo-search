package com.bsimsek.githubreposearch.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsimsek.githubreposearch.data.network.DataHolder

abstract class BaseViewModel<T : DataHolder> : ViewModel() {
    protected var mUiState = MutableLiveData<T>()
    open val uiState: LiveData<T> = mUiState

}