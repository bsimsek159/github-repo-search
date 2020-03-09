package com.bsimsek.githubreposearch.domain

import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.google.gson.annotations.SerializedName

data class GithubRepoResponse(
    @SerializedName("total_count")
    val totalCount: Int?,
    @SerializedName("items")
    val items: List<GithubRepo>? = null
)