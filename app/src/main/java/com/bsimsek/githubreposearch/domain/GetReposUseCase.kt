package com.bsimsek.githubreposearch.domain

import androidx.lifecycle.LiveData
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.data.repo.GithubRepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReposUseCase @Inject constructor(private val repository: GithubRepoRepository) {
    fun getRepos(query: String) : LiveData<DataHolder<List<GithubRepo>>> {
        return repository.search(query)
    }

    fun getNextRepoPage(query: String): LiveData<DataHolder<Boolean>> {
        return repository.searchNextPage(query)
    }
}