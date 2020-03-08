package com.bsimsek.githubreposearch.data.repo

import com.bsimsek.githubreposearch.data.GithubRepoServices
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.data.network.DataHolder
import com.bsimsek.githubreposearch.domain.GithubRepoResponse
import com.bsimsek.githubreposearch.domain.GithubRepoSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubSearchRepoImpl @Inject constructor(
    private val githubRepoApi: GithubRepoServices
): BaseRepositoryImpl(), GithubRepoSearchRepository {
    override suspend fun fetchGithubRepos(query: String?): Flow<ArrayList<GithubRepo>?> = flow {
        val result = handleApiCall(
            { githubRepoApi.getRepos(query = query, order = "stars", sort = "desc") },
            errorContext = "Error fetching repos"
        )
        when (result) {
            is DataHolder.Success<*> -> {
                if (result.data is GithubRepoResponse) {
                    emit(result.data.items as ArrayList<GithubRepo>)
                }
            }
            is DataHolder.Loading -> DataHolder.Loading
            is DataHolder.Fail -> DataHolder.Fail(result.e)
        }
    }
}