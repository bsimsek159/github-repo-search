package com.bsimsek.githubreposearch.presentation.di.modules

import com.bsimsek.githubreposearch.presentation.di.modules.GithubReposFragmentModule
import com.bsimsek.githubreposearch.presentation.ui.GithubRepoSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GithubReposFragmentProvider {

    @ContributesAndroidInjector(modules =[(GithubReposFragmentModule::class)])
    internal abstract fun provideGithubRepoSearchFragmentFactory(): GithubRepoSearchFragment

}