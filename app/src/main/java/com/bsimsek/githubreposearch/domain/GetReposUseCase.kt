package com.bsimsek.githubreposearch.domain

import com.bsimsek.githubreposearch.core.data.DataHolder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReposUseCase @Inject constructor(private val repository: GithubRepoSearchRepository) {
    suspend fun getRepos(query: String? = null, page: Int, perPage: Int) : Flow<DataHolder<*>?> {
        return repository.fetchGithubRepos(query, page, perPage)
    }
}