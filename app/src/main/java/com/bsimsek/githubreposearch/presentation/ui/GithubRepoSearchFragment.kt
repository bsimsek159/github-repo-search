package com.bsimsek.githubreposearch.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsimsek.githubreposearch.R
import com.bsimsek.githubreposearch.core.presentation.extensions.setup
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.core.presentation.base.BaseFragment
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel
import kotlinx.android.synthetic.main.fragment_github_repo_search.*
import javax.inject.Inject

class GithubRepoSearchFragment : BaseFragment<GithubRepoSearchViewModel>() {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: GithubRepoAdapter

    private var itemClickListener: (View, GithubRepo) -> Unit = { _: View, item: GithubRepo ->
        Toast.makeText(requireContext(), item.owner?.loginName, Toast.LENGTH_SHORT).show()
    }

    override fun getLayoutRes(): Int =
        R.layout.fragment_github_repo_search

    override fun getViewModel(): GithubRepoSearchViewModel =
        ViewModelProviders.of(this, mViewModelFactory).get(GithubRepoSearchViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setSearchListener()
    }

    private fun observeData() {
        getViewModel().uiState.observe(this, Observer {
            when (it) {
                is DataHolder.Loading -> showBlockingPane()
                is DataHolder.Success -> {
                    hideBlockingPane()
                    setUiState(true)
                    val githubRepos = ArrayList<GithubRepo>()
                    if (it.data is ArrayList<*>) {
                        it.data.forEach { item ->
                            if (item is GithubRepo) {
                                githubRepos.add(item)
                            }
                        }
                    }
                    adapter.updateItems(githubRepos)
                }
                is DataHolder.Fail -> {
                    hideBlockingPane()
                    setUiState(false)
                    tvEmptyResult.text = resources.getString(R.string.empty_search_result_text)
                }
            }
        })
    }

    override fun initView() {
        adapter.itemClickListener = itemClickListener
        val decorator = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
            decorator.setDrawable(
                it
            )
        }
        rvGithubRepo.setup(context = requireContext(), adapter = adapter)
        rvGithubRepo.addItemDecoration(decorator)
    }

    private fun setUiState(success: Boolean) {
        rvGithubRepo.isVisible = success
        tvEmptyResult.isVisible = !success
    }

    private fun setSearchListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery(query, false)
                query?.let {
                    if(it.isNotEmpty()) getViewModel().getRepos(it)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}