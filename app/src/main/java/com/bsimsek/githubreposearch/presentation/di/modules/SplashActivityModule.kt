package com.bsimsek.githubreposearch.presentation.di.modules

import com.bsimsek.githubreposearch.presentation.ui.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashActivityModule {
    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity
}