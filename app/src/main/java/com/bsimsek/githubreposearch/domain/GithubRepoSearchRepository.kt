package com.bsimsek.githubreposearch.domain

import com.bsimsek.githubreposearch.data.model.GithubRepo
import kotlinx.coroutines.flow.Flow

interface GithubRepoSearchRepository {
    suspend fun fetchGithubRepos(query: String): Flow<ArrayList<GithubRepo>?>
}