package com.bsimsek.githubreposearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bsimsek.githubreposearch.data.model.GithubRepo

@Database(
    entities = [GithubRepo::class,
        GithubRepoResult::class],
    version = 1,
    exportSchema = false
)
abstract class GithubRepoDb : RoomDatabase() {
    abstract fun repoDao(): GithubDao
}