package com.bsimsek.githubreposearch.core.presentation.extensions

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.bsimsek.githubreposearch.R

fun Activity.createProgress(isCancelable: Boolean = false): Dialog = Dialog(this).apply {
    setCancelable(isCancelable)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setContentView(R.layout.view_progress)
}