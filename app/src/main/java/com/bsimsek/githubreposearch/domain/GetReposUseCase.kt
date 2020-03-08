package com.bsimsek.githubreposearch.domain

import com.bsimsek.githubreposearch.data.model.GithubRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReposUseCase @Inject constructor(val repository: GithubRepoSearchRepository) {

    suspend fun getRepos(query: String? = null) : Flow<ArrayList<GithubRepo>?> {
        return  repository.fetchGithubRepos(query)
    }
}