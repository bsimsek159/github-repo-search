package com.bsimsek.githubreposearch.presentation.di

import com.bsimsek.githubreposearch.presentation.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [(GithubReposFragmentProvider::class)])
    internal abstract fun bindMainActivity(): MainActivity
}