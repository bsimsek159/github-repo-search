package com.bsimsek.githubreposearch.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.bsimsek.githubreposearch.core.EmptyLiveData
import com.bsimsek.githubreposearch.core.data.ApiSuccessResponse
import com.bsimsek.githubreposearch.core.data.AppExecutors
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.core.data.NetworkBoundResource
import com.bsimsek.githubreposearch.data.GithubRepoServices
import com.bsimsek.githubreposearch.data.db.GithubDao
import com.bsimsek.githubreposearch.data.db.GithubRepoDb
import com.bsimsek.githubreposearch.data.db.GithubRepoResult
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.domain.GithubRepoResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepoRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: GithubRepoDb,
    private val repoDao: GithubDao,
    private val services: GithubRepoServices
){
    fun searchNextPage(query: String): LiveData<DataHolder<Boolean>> {
        val nextPageTask = FetchNextPage(
            query = query,
            services = services,
            db = db
        )
        appExecutors.networkIO().execute(nextPageTask)
        return nextPageTask.liveData
    }

    fun search(query: String): LiveData<DataHolder<List<GithubRepo>>> {
        return object : NetworkBoundResource<List<GithubRepo>, GithubRepoResponse>(appExecutors) {
            override fun saveCallResult(item: GithubRepoResponse) {
                val repoIds = item.items.map { it.id }
                val repoSearchResult = GithubRepoResult(
                    query = query,
                    repoIds = repoIds,
                    totalCount = item.totalCount,
                    next = item.nextPage
                )
                db.runInTransaction {
                    repoDao.insertRepos(item.items)
                    repoDao.insert(repoSearchResult)
                }
            }

            override fun shouldFetch(data: List<GithubRepo>?) = data == null

            override fun loadFromDb(): LiveData<List<GithubRepo>> {
                return repoDao.search(query).switchMap { searchData ->
                    if (searchData == null) {
                        EmptyLiveData.create()
                    } else {
                        repoDao.loadRepos(searchData.repoIds)
                    }
                }
            }

            override fun createCall() = services.getRepos(query)

            override fun processResponse(response: ApiSuccessResponse<GithubRepoResponse>)
                    : GithubRepoResponse {
                val body = response.body
                body.nextPage = response.nextPage
                return body
            }
        }.asLiveData()
    }
}