package com.bsimsek.githubreposearch.presentation.viewModel

import androidx.lifecycle.*
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import com.bsimsek.githubreposearch.domain.GithubRepoResponse
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel.Companion.PAGE_FIRST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.roundToInt

class GithubRepoSearchViewModel @Inject constructor(private val getRepos: GetReposUseCase) :
    ViewModel() {

    private val _repoListLiveData = MediatorLiveData<ArrayList<GithubRepo>>()
    private val _uiStateLiveData = MutableLiveData<DataHolder<*>>()
    private val _pageLiveData = MutableLiveData<Int?>()
    private val pageRepo = PageRepo()
    private var queryString: String? = null
    private var isNewSearchSubmitted = false

    val repoListLiveData: LiveData<ArrayList<GithubRepo>>
        get() = _repoListLiveData

    val uiStateLiveData: LiveData<DataHolder<*>>
        get() = _uiStateLiveData

    companion object {
        const val PAGE_FIRST = 1
        const val PAGE_PER_ITEM = 30
    }

    fun initPagination(query: String, perPage: Int) {
        queryString = query
        _repoListLiveData.addSource(_pageLiveData) { page ->
            page?.let { getRepos(query = queryString, nextPage = it, perPage = perPage) }
        }
    }

    fun getReposByPagination(query: String) {
        if (_pageLiveData.value == null) {
            _pageLiveData.value = pageRepo.currentPage
        } else {
            val nextPage = if (query != queryString && isNewSearchSubmitted) {
                isNewSearchSubmitted = false
                0
            } else pageRepo.currentPage + 1
            if (!pageRepo.isNextPage) return
            _pageLiveData.value = nextPage
        }
    }

    fun getRepos(query: String? = null, nextPage: Int, perPage: Int) {
        _uiStateLiveData.value = DataHolder.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getRepos.getRepos(query, page = nextPage, perPage = perPage).collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is DataHolder.Loading -> _uiStateLiveData.value = DataHolder.Loading
                        is DataHolder.Success -> {
                            val githubRepos = ArrayList<GithubRepo>()
                            if (it.data is GithubRepoResponse) {
                                pageRepo.currentPage = nextPage
                                pageRepo.isNextPage =
                                    nextPage < getRemainingPage(it.data.totalCount, perPage)
                                it.data.items.takeIf { !it.isNullOrEmpty() }?.let {
                                    githubRepos.addAll(it)
                                }
                            }
                            _repoListLiveData.value = githubRepos
                            _uiStateLiveData.value = DataHolder.Success(it.data)
                        }
                        is DataHolder.Fail -> _uiStateLiveData.value = DataHolder.Fail(it.e)
                    }
                }
            }
        }
    }

    private fun getRemainingPage(totalCount: Int, perPage: Int): Int {
        val totalPage = ceil((totalCount.toDouble() / perPage.toDouble())).roundToInt()
        return totalPage - pageRepo.currentPage
    }

    fun removeLiveDataSource() {
        _pageLiveData.value = null
        _repoListLiveData.removeSource(_pageLiveData)
        isNewSearchSubmitted = true
        resetForNewSearch()
    }

    private fun resetForNewSearch() {
        pageRepo.currentPage = PAGE_FIRST
        pageRepo.isNextPage = true
    }
}

data class PageRepo(var currentPage: Int = PAGE_FIRST, var isNextPage: Boolean = true)