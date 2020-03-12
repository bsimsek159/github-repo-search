package com.bsimsek.githubreposearch.presentation.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bsimsek.githubreposearch.core.AppConstants.Companion.BASE_URL
import com.bsimsek.githubreposearch.core.LiveDataCallAdapterFactory
import com.bsimsek.githubreposearch.data.GithubRepoServices
import com.bsimsek.githubreposearch.data.db.GithubDao
import com.bsimsek.githubreposearch.data.db.GithubRepoDb
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): GithubRepoServices {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubRepoServices::class.java)
    }

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context{
        return application
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): GithubRepoDb {
        return Room
            .databaseBuilder(app, GithubRepoDb::class.java, "github.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: GithubRepoDb): GithubDao {
        return db.repoDao()
    }
}