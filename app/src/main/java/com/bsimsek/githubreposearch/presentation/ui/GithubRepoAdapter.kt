package com.bsimsek.githubreposearch.presentation.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bsimsek.githubreposearch.R
import com.bsimsek.githubreposearch.data.model.GithubRepo
import com.bumptech.glide.Glide
import javax.inject.Inject

class GithubRepoAdapter @Inject constructor(
    private val repoList: MutableList<GithubRepo>
) : RecyclerView.Adapter<GithubRepoAdapter.GithubRepoViewHolder>() {
    var itemClickListener: ((view: View, item: GithubRepo) -> Unit)? = null
    override fun getItemCount(): Int = repoList.size

    fun updateAllItems(itemList: ArrayList<GithubRepo>) {
        if (!itemList.isNullOrEmpty()) {
            clearItems()
            repoList.addAll(itemList)
            notifyDataSetChanged()
        }
    }

    private fun clearItems() {
        repoList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_github_repo, parent, false)
        return GithubRepoViewHolder(view)
    }

    inner class GithubRepoViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var container: ConstraintLayout = itemView.findViewById(R.id.itemContainer)
        var imgView: ImageView = itemView.findViewById(R.id.avatarImg)
        var tvLoginName: AppCompatTextView = itemView.findViewById(R.id.tvLoginName)
        var tvRepoName: AppCompatTextView = itemView.findViewById(R.id.tvRepoName)
    }

    override fun onBindViewHolder(holder: GithubRepoViewHolder, position: Int) {
        val repo = repoList[position]

        val avatar = repo.owner?.avatar_url
        val loginName = repo.owner?.loginName
        val repoName = repo.name

        avatar?.let {
            Glide.with(holder.imgView.context).load(Uri.parse(it)).into(holder.imgView)
        }

        loginName?.let { holder.tvLoginName.text = it }
        repoName?.let { holder.tvRepoName.text = it }

        holder.container.setOnClickListener {
            itemClickListener?.invoke(it, repo)
        }
    }
}