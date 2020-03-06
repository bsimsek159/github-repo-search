package com.bsimsek.githubreposearch.data

import com.bsimsek.githubreposearch.domain.GithubRepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubRepoServices {
    companion object {
        const val SEARCH_REPO_QUERY: String = ("search/repositories?q={query}+language:assembly&sort=stars&order=desc")
    }

    @GET(SEARCH_REPO_QUERY)
    suspend fun getRepos(@Path("query") query: String): Response<GithubRepoResponse>
}