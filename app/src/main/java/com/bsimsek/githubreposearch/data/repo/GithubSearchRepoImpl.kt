package com.bsimsek.githubreposearch.data.repo

import com.bsimsek.githubreposearch.core.data.BaseRepositoryImpl
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.data.GithubRepoServices
import com.bsimsek.githubreposearch.domain.GithubRepoSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubSearchRepoImpl @Inject constructor(
    private val githubRepoApi: GithubRepoServices
): BaseRepositoryImpl(), GithubRepoSearchRepository {
    override suspend fun fetchGithubRepos(query: String?, page: Int, perPage: Int): Flow<DataHolder<*>?> = flow {
        val result =
            handleApiCall { githubRepoApi.getRepos(query = query, order = QUERY_ORDER, sort = QUERY_SORT,page = page,perPage = perPage) }
        val dataHolder: DataHolder<*>?
        dataHolder =  when (result) {
            is DataHolder.Loading -> DataHolder.Loading
            is DataHolder.Success -> DataHolder.Success(result.data)
            is DataHolder.Fail -> DataHolder.Fail(result.e)
            else -> null
        }

        return@flow emit(dataHolder)
    }

    companion object {
        const val QUERY_ORDER = "stars"
        const val QUERY_SORT = "desc"
    }
}