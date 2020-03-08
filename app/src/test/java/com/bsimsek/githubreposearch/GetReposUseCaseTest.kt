package com.bsimsek.githubreposearch

import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import com.bsimsek.githubreposearch.domain.GithubRepoSearchRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class GetReposUseCaseTest {

    private val mockRepo: GithubRepoSearchRepository = mock()
    private val getReposUseCase by lazy { GetReposUseCase(mockRepo) }

    @ExperimentalCoroutinesApi
    @Test
    fun getReposSuccess() {
        runBlockingTest {
            val list = ArrayList<GithubRepo> ()
            whenever(getReposUseCase.getRepos("")).thenReturn(flow { emit(list) })
            getReposUseCase.getRepos("").collect {
                assert(it == list)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getReposReturnNull() {
        runBlockingTest {
            val list = ArrayList<GithubRepo> ()
            whenever(getReposUseCase.getRepos("")).thenReturn(flow { emit(null) })
            getReposUseCase.getRepos("").collect {
                assert(it == null)
            }
        }
    }
}