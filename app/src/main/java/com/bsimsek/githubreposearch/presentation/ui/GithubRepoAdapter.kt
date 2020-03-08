package com.bsimsek.githubreposearch.presentation.ui

import android.content.Context
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
    private val repoList: MutableList<GithubRepo>,
    val context: Context
) :
    RecyclerView.Adapter<GithubRepoAdapter.GithubRepoViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)
    var itemClickListener: ((view: View, item: GithubRepo) -> Unit)? = null

    override fun getItemCount(): Int = repoList.size

    fun updateItems(itemList: ArrayList<GithubRepo>) {
        if (!itemList.isNullOrEmpty()) {
            clearItems()
            repoList.addAll(itemList)
            notifyDataSetChanged()
        }
    }

    private fun clearItems() {
        repoList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoViewHolder =
        GithubRepoViewHolder(layoutInflater.inflate(R.layout.item_github_repo, parent, false))

    inner class GithubRepoViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var container: ConstraintLayout = itemView.findViewById(R.id.itemContainer)
        var imgView: ImageView = itemView.findViewById(R.id.avatarImg)
        var tvLoginName: AppCompatTextView = itemView.findViewById(R.id.tvLoginName)
        var tvRepoName: AppCompatTextView = itemView.findViewById(R.id.tvRepoName)
    }

    override fun onBindViewHolder(holder: GithubRepoViewHolder, position: Int) {
        val avatar = repoList[position].owner?.avatar_url
        val loginName = repoList[position].owner?.loginName
        val repoName = repoList[position].name

        avatar?.let {
            Glide.with(context).load(Uri.parse(it)).into(holder.imgView)
        }

        loginName?.let { holder.tvLoginName.text = it}
        repoName?.let { holder.tvRepoName.text = it }

        holder.container.setOnClickListener {
            itemClickListener?.invoke(it,repoList[position])
        }
    }
}