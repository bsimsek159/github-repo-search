package com.bsimsek.githubreposearch.data.repo

import com.bsimsek.githubreposearch.core.data.BaseRepositoryImpl
import com.bsimsek.githubreposearch.data.GithubRepoServices
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.domain.GithubRepoSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubSearchRepoImpl @Inject constructor(
    private val githubRepoApi: GithubRepoServices
): BaseRepositoryImpl(), GithubRepoSearchRepository {
    override suspend fun fetchGithubRepos(query: String?): Flow<ArrayList<GithubRepo>?> = flow {
        val result = handleApiCall { githubRepoApi.getRepos(query = query, order = "stars", sort = "desc") }
        if (result != null) {
            emit(result.items as ArrayList<GithubRepo>)
        }
    }
}