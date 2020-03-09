package com.bsimsek.githubreposearch.core.presentation.extensions

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setup(
    context: Context,
    decoration: RecyclerView.ItemDecoration? = null,
    adapter: RecyclerView.Adapter<*>?
) {
    val layoutManager = LinearLayoutManager(context)
    this.layoutManager = layoutManager
    this.setHasFixedSize(false)
    adapter?.let {
        this.adapter = adapter
    }
    decoration?.let {
        addItemDecoration(decoration)
    }
}
