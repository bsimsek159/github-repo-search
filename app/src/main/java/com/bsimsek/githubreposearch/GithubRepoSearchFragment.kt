package com.bsimsek.githubreposearch

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bsimsek.githubreposearch.presentation.base.BaseFragment
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel
import javax.inject.Inject

class GithubRepoSearchFragment : BaseFragment<GithubRepoSearchViewModel>() {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    override fun getLayoutRes(): Int = R.layout.fragment_github_repo_search

    override fun getViewModel(): GithubRepoSearchViewModel =
        ViewModelProviders.of(this, mViewModelFactory).get(GithubRepoSearchViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().getRepos("mvvm")
    }
}