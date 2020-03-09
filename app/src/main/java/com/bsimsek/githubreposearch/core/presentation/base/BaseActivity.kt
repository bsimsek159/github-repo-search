package com.bsimsek.githubreposearch.core.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity(),
    BaseFragment.Callback {

    @LayoutRes
    abstract fun getLayoutRes(): Int
    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
    }
}