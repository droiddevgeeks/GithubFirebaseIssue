package com.example.githubfirebaseissue.di.module

import android.content.Context
import android.util.Log
import com.example.githubfirebaseissue.GithubApplication
import com.example.githubfirebaseissue.api.ApiConstant.Companion.HEADER_CACHE_CONTROL
import com.example.githubfirebaseissue.api.ApiConstant.Companion.HEADER_PRAGMA
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import okhttp3.CacheControl
import com.example.githubfirebaseissue.helper.ApplicationUtil
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
class InterceptorModule {

    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { provideOfflineCacheInterceptor(context, it) }
            .addNetworkInterceptor { provideCacheInterceptor(context, it) }
            .cache(provideCache(context))
            .build()
    }


    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


    private fun provideCache(context: Context): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(context.cacheDir, "http-cache"), 10 * 1024 * 1024)
        } catch (e: Exception) {
            Log.e("Cache", "Could not create Cache!")
        }
        return cache
    }

    private fun provideOfflineCacheInterceptor(
        context: Context,
        chain: Interceptor.Chain
    ): Response {
        var request = chain.request()

        if (!ApplicationUtil.hasNetwork(context)) {
            val cacheControl = CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()
        }

        return chain.proceed(request)
    }

    private fun provideCacheInterceptor(
        context: Context,
        chain: Interceptor.Chain
    ): Response {
        val response = chain.proceed(chain.request())
        val cacheControl: CacheControl = if (ApplicationUtil.hasNetwork(context)) {
            CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()
        } else {
            CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .build()
        }

        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }
}