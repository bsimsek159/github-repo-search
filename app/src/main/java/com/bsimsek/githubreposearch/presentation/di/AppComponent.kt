package com.bsimsek.githubreposearch.presentation.di

import android.app.Application
import com.bsimsek.githubreposearch.core.GitHubSearchApp
import com.bsimsek.githubreposearch.presentation.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class,
        SplashActivityModule::class
    ]
)

interface AppComponent {
    fun inject(app: GitHubSearchApp)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}