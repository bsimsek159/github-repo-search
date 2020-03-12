package com.bsimsek.githubreposearch.presentation.di.modules

import com.bsimsek.githubreposearch.data.GithubRepoServices
import com.bsimsek.githubreposearch.data.repo.GithubSearchRepoImpl
import com.bsimsek.githubreposearch.domain.GithubRepoSearchRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideGithubSearchRepo(githubRepoServices: GithubRepoServices): GithubRepoSearchRepository {
        return GithubSearchRepoImpl(githubRepoServices)
    }
}