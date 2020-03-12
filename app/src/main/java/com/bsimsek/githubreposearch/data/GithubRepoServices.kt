package com.bsimsek.githubreposearch.data

import com.bsimsek.githubreposearch.domain.GithubRepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepoServices {
    companion object {
        const val SEARCH_REPO_QUERY = "search/repositories"
    }

    @GET(SEARCH_REPO_QUERY)
    suspend fun getRepos(@Query("q") query: String? = null,
                         @Query("sort") sort: String,
                         @Query("order") order: String,
                         @Query("page") page: Int,
                         @Query("per_page") perPage: Int
    ): Response<GithubRepoResponse?>
}