package com.bsimsek.githubreposearch.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bsimsek.githubreposearch.R
import com.bsimsek.githubreposearch.presentation.base.BaseActivity
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector

class MainActivity : BaseActivity<GithubRepoSearchViewModel>(), HasSupportFragmentInjector {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun getLayoutRes(): Int = R.layout.activity_main


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getViewModel(): GithubRepoSearchViewModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
