package com.bsimsek.githubreposearch.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.bsimsek.githubreposearch.core.data.ApiResultException
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import com.bsimsek.githubreposearch.core.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepoSearchViewModel @Inject constructor(private val getRepos: GetReposUseCase) :
    BaseViewModel<DataHolder<*>>() {
    fun getRepos(query: String? = null) {
        mUiState.value = DataHolder.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getRepos.getRepos(query).collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is DataHolder.Loading -> mUiState.value = DataHolder.Loading
                        is DataHolder.Success -> mUiState.value = DataHolder.Success(it.data)
                        is DataHolder.Fail -> mUiState.value = DataHolder.Fail(it.e)
                    }
//                    it?.let {
//                        mUiState.value = DataHolder.Success(it)
//                    } ?: DataHolder.Fail(ApiResultException())
                }
            }
        }
    }
}