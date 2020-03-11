package com.bsimsek.githubreposearch.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
    indices = [
        Index("id")],
        primaryKeys = ["name"]
)
data class GithubRepo(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("full_name")
    val fullName: String,
    @field:SerializedName("owner")
    @field:Embedded(prefix = "owner_")
    val owner: Owner
){
    data class Owner(
        @field:SerializedName("login")
        val loginName: String? = null,
        @field:SerializedName("avatar_url")
        val avatar_url: String? = null,
        @field:SerializedName("url")
        val url: String? = null
    )
}

