package com.bsimsek.githubreposearch.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bsimsek.githubreposearch.core.data.*
import com.bsimsek.githubreposearch.data.GithubRepoServices
import com.bsimsek.githubreposearch.data.db.GithubRepoDb
import com.bsimsek.githubreposearch.data.db.GithubRepoResult
import com.bsimsek.githubreposearch.data.model.GithubRepo
import java.io.IOException

class FetchNextPage constructor(
    private val query: String,
    private val services: GithubRepoServices,
    private val db: GithubRepoDb
): Runnable {
    private val _liveData = MutableLiveData<DataHolder<Boolean>>()
    val liveData: LiveData<DataHolder<Boolean>> = _liveData

    override fun run() {
        val current = db.repoDao().findSearchResult(query)
        if (current == null) {
            _liveData.postValue(null)
            return
        }
        val nextPage = current.next
        if (nextPage == null) {
            _liveData.postValue(DataHolder.Success(false))
            return
        }
        val newValue = try {
            val response = services.getRepos(query = query, page = nextPage).execute()
            when (val apiResponse = ApiResponse.create(response)) {
                is ApiSuccessResponse -> {
                    val repos = arrayListOf<Int>()
                    repos.addAll(current.repoIds)
                    repos.addAll(apiResponse.body.items.map { it.id })
                    val merged = GithubRepoResult(
                        query = query,
                        repoIds = repos,
                        totalCount = apiResponse.body.totalCount,
                        next = apiResponse.nextPage
                    )
                    db.runInTransaction {
                        db.repoDao().insert(merged)
                        db.repoDao().insertRepos(apiResponse.body.items)
                    }
                    DataHolder.Success(apiResponse.nextPage != null)
                }
                is ApiEmptyResponse -> {
                    DataHolder.Success(false)
                }
                is ApiErrorResponse -> {
                    DataHolder.Fail(response.toErrorMessage())
                }
            }
        } catch (e: IOException) {
            DataHolder.Fail(e.message!!)
        }
        _liveData.postValue(newValue)
    }
}