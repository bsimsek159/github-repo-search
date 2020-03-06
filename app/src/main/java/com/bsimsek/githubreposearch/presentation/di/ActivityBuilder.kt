package com.bsimsek.githubreposearch.presentation.di

import com.bsimsek.githubreposearch.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(GithubRepoSearchActivityModule::class), (GithubReposFragmentModule::class)])
    internal abstract fun bindMainActivity(): MainActivity
}