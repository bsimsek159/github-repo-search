package com.bsimsek.githubreposearch.presentation.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bsimsek.githubreposearch.databinding.ItemGithubRepoBinding
import com.bumptech.glide.Glide
import javax.inject.Inject

class GithubRepoAdapter @Inject constructor(
    private val repoList: MutableList<GithubRepo>
) : RecyclerView.Adapter<GithubRepoAdapter.GithubRepoViewHolder>() {
    var itemClickListener: ((view: View, item: GithubRepo?) -> Unit)? = null
    private lateinit var itemGithubRepoBinding: ItemGithubRepoBinding
    override fun getItemCount(): Int = repoList.size

    fun updateAllItems(itemList: List<GithubRepo>) {
        if (!itemList.isNullOrEmpty()) {
            repoList.addAll(itemList)
            notifyDataSetChanged()
        }
    }

    private fun clearItems() {
        repoList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoViewHolder {
        itemGithubRepoBinding = ItemGithubRepoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GithubRepoViewHolder(itemGithubRepoBinding)
    }

    inner class GithubRepoViewHolder internal constructor(private val itemBinding: ItemGithubRepoBinding) :
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(model: GithubRepo?) {
            with(itemBinding) {
                model?.owner?.avatar_url?.let { Glide.with(this.avatarImg.context).load(Uri.parse(it)).into(this.avatarImg) }
                model?.owner?.loginName?.let { this.tvLoginName.text = it }
                model?.name?.let { this.tvRepoName.text = it }
                this.itemContainer.setOnClickListener {
                    itemClickListener?.invoke(it, model)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: GithubRepoViewHolder, position: Int) {
        holder.bind(repoList[position])
    }
}