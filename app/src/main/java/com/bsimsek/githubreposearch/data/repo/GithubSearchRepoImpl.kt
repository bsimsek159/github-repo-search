package com.bsimsek.githubreposearch.data.repo

import com.bsimsek.githubreposearch.data.GithubRepoServices
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.data.network.DataHolder
import com.bsimsek.githubreposearch.domain.GithubRepoSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubSearchRepoImpl @Inject constructor(
    private val githubRepoApi: GithubRepoServices
): BaseRepositoryImpl(), GithubRepoSearchRepository {
    override suspend fun fetchGithubRepos(query: String): Flow<ArrayList<GithubRepo>?> = flow {
        val data = handleApiCall({githubRepoApi.getRepos(query = query, order = "stars", sort = "desc")}, errorContext = "Error fetching repos")
        if (data != null) {
            emit(data.items as ArrayList<GithubRepo>)
        }
    }
}