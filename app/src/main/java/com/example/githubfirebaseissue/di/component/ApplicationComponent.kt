package com.example.githubfirebaseissue.di.component

import com.example.githubfirebaseissue.di.module.ApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface ApplicationComponent
