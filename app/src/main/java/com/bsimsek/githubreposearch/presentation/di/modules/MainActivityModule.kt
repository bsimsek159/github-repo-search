package com.bsimsek.githubreposearch.presentation.di.modules

import com.bsimsek.githubreposearch.presentation.di.modules.GithubReposFragmentProvider
import com.bsimsek.githubreposearch.presentation.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [(GithubReposFragmentProvider::class)])
    internal abstract fun bindMainActivity(): MainActivity
}