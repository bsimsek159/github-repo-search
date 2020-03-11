package com.bsimsek.githubreposearch.data.db

import androidx.room.Entity
import androidx.room.TypeConverters

@Entity(primaryKeys = ["query"])
@TypeConverters(GithubTypeConverters::class)
data class GithubRepoResult(
    val query: String,
    val repoIds: List<Int>,
    val totalCount: Int,
    val next: Int?
)