package com.bsimsek.githubreposearch.data.model

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("full_name")
    val fullName: String ? = null,
    @SerializedName("owner")
    val owner: Owner? = null
)

data class Owner(
    @SerializedName("login")
    val loginName: String? = null,
    @SerializedName("avatar_url")
    val avatar_url: String? = null,
    @SerializedName("url")
    val url: String? = null
)