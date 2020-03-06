package com.bsimsek.githubreposearch.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<V : BaseViewModel<BaseUiState>> : Fragment() {
    @LayoutRes
    abstract fun getLayoutRes(): Int
    private var mViewModel: V? = null
    abstract fun getViewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}