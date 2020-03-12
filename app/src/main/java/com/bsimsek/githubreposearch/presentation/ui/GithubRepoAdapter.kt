package com.bsimsek.githubreposearch.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bsimsek.githubreposearch.core.extension.loadImage
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.databinding.ItemGithubRepoBinding
import javax.inject.Inject

class GithubRepoAdapter @Inject constructor(
    private val repoList: MutableList<GithubRepo>
) : RecyclerView.Adapter<GithubRepoAdapter.GithubRepoViewHolder>() {
    var itemClickListener: ((view: View, item: GithubRepo?) -> Unit)? = null
    private lateinit var itemGithubRepoBinding: ItemGithubRepoBinding
    override fun getItemCount(): Int = repoList.size

    fun clearItems() = repoList.clear()

    fun addItems(itemList: ArrayList<GithubRepo>) {
        val startRange = repoList.size
        repoList.addAll(itemList)
        if (startRange == 0) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(startRange, itemList.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoViewHolder {
        itemGithubRepoBinding =
            ItemGithubRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubRepoViewHolder(itemGithubRepoBinding)
    }

    inner class GithubRepoViewHolder internal constructor(private val itemBinding: ItemGithubRepoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: GithubRepo?) {
            with(itemBinding) {
                model?.let { repo ->
                    repo.owner?.avatarUrl?.run {
                        avatarImg.loadImage(this)
                    }

                    repo.owner?.loginName?.let { this.tvLoginName.text = it }
                    repo.name?.let { this.tvRepoName.text = it }

                    this.itemContainer.setOnClickListener {
                        itemClickListener?.invoke(it, repo)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: GithubRepoViewHolder, position: Int) {
        holder.bind(repoList[position])
    }
}