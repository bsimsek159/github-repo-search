package com.bsimsek.githubreposearch.presentation.viewModel

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.bsimsek.githubreposearch.core.EmptyLiveData
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class GithubRepoSearchViewModel @Inject constructor(private val getReposUseCase: GetReposUseCase) :
    ViewModel() {

//    private val _query = MutableLiveData<String>()
//    private val _repoListLiveData = MutableLiveData<ArrayList<GithubRepo>>()
//    private val _uiStateLiveData = MutableLiveData<DataHolder<*>>()
//
//    val repoListLiveData: LiveData<ArrayList<GithubRepo>>
//        get() = _repoListLiveData
//
//    val uiStateLiveData: LiveData<DataHolder<*>>
//        get() = _uiStateLiveData
//
//    fun getRepos(query: String? = null) {
//        _uiStateLiveData.value = DataHolder.Loading
//        viewModelScope.launch(Dispatchers.IO) {
//            getRepos.getRepos(query).collect {
//                withContext(Dispatchers.Main) {
//                    when (it) {
//                        is DataHolder.Loading -> _uiStateLiveData.value = DataHolder.Loading
//                        is DataHolder.Success -> {
//                            if (it.data is ArrayList<*>) {
//                                _repoListLiveData.value = updateList(it.data)
//                            }
//                            _uiStateLiveData.value = DataHolder.Success(it.data)
//                        }
//                        is DataHolder.Fail -> _uiStateLiveData.value = DataHolder.Fail(it.e)
//                    }
//                }
//            }
//        }
//    }

    private val _query = MutableLiveData<String>()
    private val nextPageHandler = PaginationHandler(getReposUseCase)

    val query : LiveData<String> = _query

    val results: LiveData<DataHolder<List<GithubRepo>>> = _query.switchMap { search ->
        if (search.isBlank()) {
            EmptyLiveData.create()
        } else {
            getReposUseCase.getRepos(query = search)
        }
    }

    val loadMoreStatus: LiveData<LoadMoreState>
        get() = nextPageHandler.loadMoreState

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query.value) {
            return
        }
        nextPageHandler.reset()
        _query.value = input
    }

    fun loadNextPage() {
        _query.value?.let {
            if (it.isNotBlank()) {
                nextPageHandler.queryNextPage(it)
            }
        }
    }

    fun refresh() {
        _query.value?.let {
            _query.value = it
        }
    }


    private fun updateList(data: ArrayList<*>): ArrayList<GithubRepo> {
        val githubRepos = ArrayList<GithubRepo>()
        data.forEach { item ->
            if (item is GithubRepo) {
                githubRepos.add(item)
            }
        }
        return githubRepos
    }

    class LoadMoreState(val isRunning: Boolean, val errorMessage: String?) {
        private var handledError = false

        val errorMessageIfNotHandled: String?
            get() {
                if (handledError) {
                    return null
                }
                handledError = true
                return errorMessage
            }
    }

    class PaginationHandler(private val getReposUseCase: GetReposUseCase) : Observer<DataHolder<Boolean>> {
        private var nextPageLiveData: LiveData<DataHolder<Boolean>>? = null
        val loadMoreState = MutableLiveData<LoadMoreState>()
        private var query: String? = null
        private var _hasMore: Boolean = false

        val hasMore
            get() = _hasMore

        init {
            reset()
        }

        fun queryNextPage(query: String) {
            if (this.query == query) {
                return
            }
            unregister()
            this.query = query
            nextPageLiveData = getReposUseCase.getNextRepoPage(query)
            loadMoreState.value = LoadMoreState(
                isRunning = true,
                errorMessage = null
            )
            nextPageLiveData?.observeForever(this)
        }

        override fun onChanged(result: DataHolder<Boolean>?) {
            if (result == null) {
                reset()
            } else {
                when (result) {
                    is DataHolder.Loading -> { }
                    is DataHolder.Success -> {
                        _hasMore = result.data == true
                        unregister()
                        loadMoreState.setValue(
                            LoadMoreState(
                                isRunning = false,
                                errorMessage = null
                            )
                        )
                    }
                    is DataHolder.Fail -> {
                        _hasMore = true
                        unregister()
                        loadMoreState.setValue(
                            LoadMoreState(
                                isRunning = false,
                                errorMessage = result.error
                            )
                        )
                    }
                }
            }
        }

        private fun unregister() {
            nextPageLiveData?.removeObserver(this)
            nextPageLiveData = null
            if (_hasMore) {
                query = null
            }
        }

        fun reset() {
            unregister()
            _hasMore = true
            loadMoreState.value = LoadMoreState(
                isRunning = false,
                errorMessage = null
            )
        }
    }
}