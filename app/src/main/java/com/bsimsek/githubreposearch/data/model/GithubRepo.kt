package com.bsimsek.githubreposearch.data.model

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("full_name")
    val full_name: String ? = null,
    @SerializedName("owner")
    val owner: Owner
)

data class Owner(
    @SerializedName("login")
    val loginName: String,
    @SerializedName("avatar_url")
    val avatar_url: String? = null,
    @SerializedName("url")
    val url: String? = null
)