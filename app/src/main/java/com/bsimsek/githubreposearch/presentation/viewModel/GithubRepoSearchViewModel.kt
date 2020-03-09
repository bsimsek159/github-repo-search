package com.bsimsek.githubreposearch.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import com.bsimsek.githubreposearch.core.presentation.base.BaseViewModel
import com.bsimsek.githubreposearch.data.network.EmptySearchResultException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepoSearchViewModel @Inject constructor(private val getRepos : GetReposUseCase) : BaseViewModel<DataHolder<*>>() {
    fun getRepos(query: String? = null) {
        mUiState.value = DataHolder.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getRepos.getRepos(query).collect {
                withContext(Dispatchers.Main) {
                    if (it != null) {
                        mUiState.value = DataHolder.Success(it)
                    } else {
                        mUiState.value = DataHolder.Fail(EmptySearchResultException())
                    }
                }
            }
        }
    }
}