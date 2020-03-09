package com.bsimsek.githubreposearch.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepoSearchViewModel @Inject constructor(private val getRepos: GetReposUseCase) :
    ViewModel() {

    private val _repoListLiveData = MutableLiveData<ArrayList<GithubRepo>>()
    private val _uiStateLiveData = MutableLiveData<DataHolder<*>>()

    val repoListLiveData: LiveData<ArrayList<GithubRepo>>
        get() = _repoListLiveData

    val uiStateLiveData: LiveData<DataHolder<*>>
        get() = _uiStateLiveData

    fun getRepos(query: String? = null) {
        _uiStateLiveData.value = DataHolder.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getRepos.getRepos(query).collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is DataHolder.Loading -> _uiStateLiveData.value = DataHolder.Loading
                        is DataHolder.Success -> {
                            if (it.data is ArrayList<*>) {
                                _repoListLiveData.value = updateList(it.data)
                            }
                            _uiStateLiveData.value = DataHolder.Success(it.data)
                        }
                        is DataHolder.Fail -> _uiStateLiveData.value = DataHolder.Fail(it.e)
                    }
                }
            }
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
}