package com.bsimsek.githubreposearch.data.db

import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bsimsek.githubreposearch.data.model.GithubRepo

@Dao
abstract class GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<GithubRepo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repo: GithubRepoResult)

    @Query("SELECT * FROM GithubRepoResult WHERE `query` = :query")
    abstract fun search(query: String): LiveData<GithubRepoResult?>

    @Query("SELECT * FROM GithubRepoResult WHERE `query` = :query")
    abstract fun findSearchResult(query: String): GithubRepoResult?

    fun loadRepos(repoIds: List<Int>): LiveData<List<GithubRepo>> {
        val order = SparseIntArray()
        repoIds.withIndex().forEach {
            order.put(it.value, it.index)
        }
        return loadById(repoIds).map { repositories ->
            repositories.sortedWith(compareBy { order.get(it.id) })
        }
    }

    @Query("SELECT * FROM GithubRepo WHERE id in (:repoIds)")
    protected abstract fun loadById(repoIds: List<Int>): LiveData<List<GithubRepo>>

}