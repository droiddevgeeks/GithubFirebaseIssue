package com.example.githubfirebaseissue.di.module

import com.example.githubfirebaseissue.api.GithubApi
import com.example.githubfirebaseissue.repository.GithubRepository
import com.example.githubfirebaseissue.repository.GithubRepositoryImpl
import com.example.githubfirebaseissue.usecase.GetFireBaseIssueUseCase
import com.example.githubfirebaseissue.usecase.GetFireBaseIssueUseCaseImpl
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {

    @Provides
    fun provideGithubFirebaseUseCase(githubRepository: GithubRepository): GetFireBaseIssueUseCase {
        return GetFireBaseIssueUseCaseImpl(githubRepository)
    }

    @Provides
    fun provideGithubRepository(githubApi: GithubApi): GithubRepository {
        return GithubRepositoryImpl(githubApi)
    }
}