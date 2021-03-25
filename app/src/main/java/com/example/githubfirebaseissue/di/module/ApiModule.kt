package com.example.githubfirebaseissue.di.module

import com.example.githubfirebaseissue.api.ApiConstant
import com.example.githubfirebaseissue.api.GithubApi
import com.example.githubfirebaseissue.common.BaseSchedulerProvider
import com.example.githubfirebaseissue.common.RxScheduler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module(includes = [InterceptorModule::class, RepositoryModule::class])
abstract class ApiModule {

    companion object {
        @Provides
        fun provideRetrofit(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(ApiConstant.GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }

        @Provides
        fun provideApiInterface(retrofit: Retrofit): GithubApi {
            return retrofit.create(GithubApi::class.java)
        }
    }

    @Binds
    abstract fun provideRxScheduler(scheduler: BaseSchedulerProvider): RxScheduler
}