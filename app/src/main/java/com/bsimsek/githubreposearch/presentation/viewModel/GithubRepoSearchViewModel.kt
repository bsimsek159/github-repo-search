package com.bsimsek.githubreposearch.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import com.bsimsek.githubreposearch.presentation.base.BaseUiState
import com.bsimsek.githubreposearch.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepoSearchViewModel @Inject constructor(private val getRepos : GetReposUseCase) : BaseViewModel<BaseUiState>() {

    fun getRepos(query: String) {
        mUiState.value = BaseUiState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            getRepos.getRepos(query).collect {
                withContext(Dispatchers.Main) {
                    if (it.isNullOrEmpty()) {
                        mUiState.value = BaseUiState.Fail(errorMessage = "No item found")
                    } else {
                        mUiState.value = BaseUiState.Success(it)
                    }
                }
            }
        }
    }
}