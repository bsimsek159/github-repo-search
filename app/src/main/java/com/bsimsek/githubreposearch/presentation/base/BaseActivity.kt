package com.bsimsek.githubreposearch.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseActivity<V: BaseViewModel<BaseUiState>> : AppCompatActivity(),
    BaseFragment.Callback {

    @LayoutRes
    abstract fun getLayoutRes(): Int
    private var mViewModel: V? = null
    abstract fun getViewModel(): V
    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String) {}


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}