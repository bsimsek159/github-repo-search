package com.bsimsek.githubreposearch.presentation.di.modules

import androidx.lifecycle.ViewModelProvider
import com.bsimsek.githubreposearch.core.presentation.ViewModelProviderFactory
import com.bsimsek.githubreposearch.data.repo.GithubRepoRepository
import com.bsimsek.githubreposearch.data.repo.GithubSearchRepoImpl
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import com.bsimsek.githubreposearch.presentation.ui.GithubRepoAdapter
import com.bsimsek.githubreposearch.presentation.viewModel.GithubRepoSearchViewModel
import dagger.Module
import dagger.Provides

@Module
class GithubReposFragmentModule {

    @Provides
    internal fun getGithubReposProvider(repository: GithubRepoRepository): GetReposUseCase {
        return GetReposUseCase(repository)
    }

    @Provides
    internal fun provideGithubReposViewModel(useCase: GetReposUseCase): GithubRepoSearchViewModel {
        return GithubRepoSearchViewModel(useCase)
    }

    @Provides
    internal fun provideGithubRepoAdapter(): GithubRepoAdapter {
        return GithubRepoAdapter(ArrayList())
    }

    @Provides fun provideGithubReposFragment(viewModel: GithubRepoSearchViewModel) : ViewModelProvider.Factory {
        return ViewModelProviderFactory(
            viewModel
        )
    }
}