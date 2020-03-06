package com.bsimsek.githubreposearch.presentation.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import com.bsimsek.githubreposearch.presentation.base.BaseUiState
import com.bsimsek.githubreposearch.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepoSearchViewModel @Inject constructor(val getRepos : GetReposUseCase) : BaseViewModel<BaseUiState>() {

    fun getRepos(query: String) {
        mUiState.value = BaseUiState.loading()
        viewModelScope.launch(Dispatchers.IO) {
            getRepos.getRepos(query).collect {
                withContext(Dispatchers.Main) {Log.d("aaa", it?.get(0)?.full_name.toString())}
            }
        }
    }
}