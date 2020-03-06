package com.bsimsek.githubreposearch.presentation.di

import com.bsimsek.githubreposearch.data.repo.GithubSearchRepoImpl
import com.bsimsek.githubreposearch.domain.GetReposUseCase
import dagger.Module
import dagger.Provides

@Module
class GithubReposFragmentModule {

    @Provides
    internal fun getGithubRepos(repository: GithubSearchRepoImpl): GetReposUseCase {
        return GetReposUseCase(repository)
    }
}