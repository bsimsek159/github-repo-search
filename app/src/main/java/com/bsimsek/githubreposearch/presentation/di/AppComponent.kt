package com.bsimsek.githubreposearch.presentation.di

import android.app.Application
import com.bsimsek.githubreposearch.core.GitHubSearchApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class),
    (NetworkModule::class), (RepositoryModule::class), (ActivityModule::class)])

interface AppComponent {
    fun inject(app: GitHubSearchApp)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}