package com.bsimsek.githubreposearch.presentation.di.modules

import android.content.Context
import com.bsimsek.githubreposearch.core.AppConstants.BASE_URL
import com.bsimsek.githubreposearch.core.data.RequestInterceptor
import com.bsimsek.githubreposearch.data.GithubRepoServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideRequestInterceptor(context: Context): RequestInterceptor {
        return RequestInterceptor(context)
    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        requestInterceptor: RequestInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(requestInterceptor)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideGithubRepoApi(retrofit: Retrofit): GithubRepoServices {
        return retrofit.create(GithubRepoServices::class.java)
    }
}