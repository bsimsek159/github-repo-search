package com.bsimsek.githubreposearch.presentation.di

import com.bsimsek.githubreposearch.data.repo.GithubSearchRepoImpl
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel
import dagger.Module
import dagger.Provides

@Module
class GithubRepoSearchActivityModule {
    @Provides
    internal fun provideMainViewModel(githubSearchRepo: GithubSearchRepoImpl) : GithubRepoSearchViewModel{
        return GithubRepoSearchViewModel(githubSearchRepo)
    }
}