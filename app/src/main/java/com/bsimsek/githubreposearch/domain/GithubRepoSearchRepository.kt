package com.bsimsek.githubreposearch.domain

import com.bsimsek.githubreposearch.core.data.DataHolder
import kotlinx.coroutines.flow.Flow

interface GithubRepoSearchRepository {
    suspend fun fetchGithubRepos(
        query: String? = null,
        page: Int,
        perPage: Int
    ): Flow<DataHolder<*>?>
}