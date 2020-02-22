package com.example.githubfirebaseissue.di.module

import com.example.githubfirebaseissue.ui.fragment.CommentsFragment
import com.example.githubfirebaseissue.ui.fragment.IssuesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProviderModule {

    @ContributesAndroidInjector
    abstract fun provideIssueFragment(): IssuesFragment

    @ContributesAndroidInjector
    abstract fun provideCommentFragment(): CommentsFragment
}