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
import com.bsimsek.githubreposearch.R
import com.bsimsek.githubreposearch.core.data.DataHolder
import com.bsimsek.githubreposearch.core.presentation.base.BaseFragment
import com.bsimsek.githubreposearch.core.presentation.extensions.setup
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel
import kotlinx.android.synthetic.main.fragment_github_repo_search.*
import javax.inject.Inject

class GithubRepoSearchFragment : BaseFragment<GithubRepoSearchViewModel>() {

    @Inject
    lateinit var adapter: GithubRepoAdapter

    override val layoutRes: Int get() = R.layout.fragment_github_repo_search

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
                is DataHolder.Fail -> hideProgress()
            }
        })

        viewModel.repoListLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                updateSearchResultVisibility(false)
            } else {
                adapter.updateAllItems(it)
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
        rvGithubRepo.setup(context = requireContext(),decoration = decorator, adapter = adapter)
    }

    private fun updateSearchResultVisibility(success: Boolean) {
        rvGithubRepo.isVisible = success
        tvEmptyResult.isVisible = !success
    }

    private fun setSearchListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery(query, false)
                query?.let {
                    if (it.isNotEmpty()) viewModel.getRepos(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}