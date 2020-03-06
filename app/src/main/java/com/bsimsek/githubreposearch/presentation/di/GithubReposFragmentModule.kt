package com.bsimsek.githubreposearch.presentation.di

import androidx.lifecycle.ViewModelProvider
import com.bsimsek.githubreposearch.core.ViewModelProviderFactory
import com.bsimsek.githubreposearch.data.repo.GithubSearchRepoImpl
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel
import dagger.Module
import dagger.Provides

@Module
class GithubReposFragmentModule {

    @Provides
    internal fun getGithubReposProvider(repository: GithubSearchRepoImpl): GetReposUseCase {
        return GetReposUseCase(repository)
    }

    @Provides
    internal fun provideGithubReposViewModel(getRepos: GetReposUseCase): GithubRepoSearchViewModel {
        return GithubRepoSearchViewModel(getRepos)
    }

    @Provides fun provideGithubReposFragment(viewModel: GithubRepoSearchViewModel) : ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}