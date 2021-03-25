package com.example.githubfirebaseissue.di.module

import com.example.githubfirebaseissue.repository.GithubRepository
import com.example.githubfirebaseissue.repository.GithubRepositoryImpl
import com.example.githubfirebaseissue.usecase.GetFireBaseIssueUseCase
import com.example.githubfirebaseissue.usecase.GetFireBaseIssueUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideGithubFirebaseUseCase(useCaseImpl: GetFireBaseIssueUseCaseImpl): GetFireBaseIssueUseCase

    @Binds
    abstract fun provideGithubRepository(repoImpl: GithubRepositoryImpl): GithubRepository
}