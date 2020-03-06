package com.bsimsek.githubreposearch.presentation.viewModel

import com.bsimsek.githubreposearch.data.repo.GithubSearchRepoImpl
import com.bsimsek.githubreposearch.presentation.base.BaseUiState
import com.bsimsek.githubreposearch.presentation.base.BaseViewModel
import javax.inject.Inject

class GithubRepoSearchViewModel @Inject constructor(githubSearchRepo : GithubSearchRepoImpl) :
    BaseViewModel<BaseUiState>() {
}