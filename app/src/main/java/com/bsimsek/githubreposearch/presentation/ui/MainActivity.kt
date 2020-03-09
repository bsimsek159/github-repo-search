package com.bsimsek.githubreposearch.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bsimsek.githubreposearch.R
import com.bsimsek.githubreposearch.core.presentation.base.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    override val layoutRes: Int get() = R.layout.activity_main

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
        fragmentDispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, GithubRepoSearchFragment()).commit()
    }
}
