package com.bsimsek.githubreposearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GithubReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var useCase: GetReposUseCase

    private lateinit var viewModel: GithubRepoSearchViewModel

    @Mock
    private var observer: Observer<DataHolder<*>> = mock()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        viewModel = GithubRepoSearchViewModel(useCase).apply {
            uiState.observeForever(observer)
        }
    }

    @Test
    fun testSuccessState() {
        testCoroutineRule.runWithCustomRule {
            val data = ArrayList<GithubRepo>()
            whenever(useCase.getRepos()).thenReturn(flowOf(data))

            viewModel.getRepos()

            verify(observer).onChanged(DataHolder.Loading)
            verify(observer).onChanged(DataHolder.Success(data))
        }
    }
}