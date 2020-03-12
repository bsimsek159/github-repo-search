package com.bsimsek.githubreposearch.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsimsek.githubreposearch.R
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.core.extension.setup
import com.bsimsek.githubreposearch.core.presentation.base.BaseFragment
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel.Companion.PAGE_PER_ITEM
import kotlinx.android.synthetic.main.fragment_github_repo_search.*
import javax.inject.Inject

class GithubRepoSearchFragment : BaseFragment<GithubRepoSearchViewModel>() {

    @Inject
    lateinit var adapter: GithubRepoAdapter

    override val layoutRes: Int get() = R.layout.fragment_github_repo_search
    private var isLoading = false
    private var directionDown = false
    private var lastSubmittedQuery: String? = null

    private var itemClickListener: (View, GithubRepo?) -> Unit = { _: View, item: GithubRepo? ->
        Toast.makeText(requireContext(), item?.owner?.loginName, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setSearchListener()
    }

    private fun observeData() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataHolder.Loading -> showProgress()
                is DataHolder.Success -> hideProgress()
                is DataHolder.Fail -> {
                    hideProgress()
                    Toast.makeText(requireContext(), it.e.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.repoListLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                updateSearchResultVisibility(false)
            } else {
                isLoading = false
                adapter.addItems(it)
                updateSearchResultVisibility(true)
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
        rvGithubRepo.setup(context = requireContext(), decoration = decorator, adapter = adapter)

        rvGithubRepo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                directionDown = dy > 0
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                    && !isLoading
                    && searchView.query.toString() == lastSubmittedQuery
                    && directionDown
                ) {
                    isLoading = true
                    viewModel.getReposByPagination(searchView.query.toString())
                }
            }
        })
    }

    private fun updateSearchResultVisibility(success: Boolean) {
        rvGithubRepo.isVisible = success
        tvEmptyResult.isVisible = !success
    }

    private fun setSearchListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.removeLiveDataSource()
                searchView.apply {
                    clearFocus()
                    setQuery(query, false)
                }
                query?.let {
                    if (it.isNotEmpty()) {
                        lastSubmittedQuery = it
                        adapter.clearItems()
                        viewModel.run {
                            getReposByPagination(it)
                            initPagination(it, perPage = PAGE_PER_ITEM)
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) adapter.clearItems()
                return true
            }
        })
    }
}