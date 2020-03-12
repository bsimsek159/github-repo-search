package com.bsimsek.githubreposearch.core.extension

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(Uri.parse(url))
        .into(this)
}