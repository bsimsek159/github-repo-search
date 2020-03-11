package com.bsimsek.githubreposearch.data

import androidx.lifecycle.LiveData
import com.bsimsek.githubreposearch.core.data.ApiResponse
import com.bsimsek.githubreposearch.domain.GithubRepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepoServices {
    companion object {
        const val SEARCH_REPO_QUERY = "search/repositories"
    }

    @GET(SEARCH_REPO_QUERY)
    fun getRepos(@Query("q") query: String,
                         @Query("page") page: Int
                         ): Call<GithubRepoResponse>

    @GET(SEARCH_REPO_QUERY)
    fun getRepos(@Query("q") query: String): LiveData<ApiResponse<GithubRepoResponse>>
}