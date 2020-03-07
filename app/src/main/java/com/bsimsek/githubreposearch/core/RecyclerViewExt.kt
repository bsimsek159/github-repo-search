package com.bsimsek.githubreposearch.core

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setup(
    context: Context,
    adapter: RecyclerView.Adapter<*>?
) {
    val layoutManager = LinearLayoutManager(context)
    this.layoutManager = layoutManager
    this.setHasFixedSize(false)
    adapter?.let {
        this.adapter = adapter
    }
}
