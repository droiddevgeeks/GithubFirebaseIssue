package com.example.githubfirebaseissue.di.component

import com.example.githubfirebaseissue.GithubApplication
import com.example.githubfirebaseissue.di.module.ApiModule
import com.example.githubfirebaseissue.di.module.ActivityBindingModule
import com.example.githubfirebaseissue.di.module.ViewModelModule
import com.example.githubfirebaseissue.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        ApiModule::class]
)
interface ApplicationComponent : AndroidInjector<GithubApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: GithubApplication): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(app: GithubApplication)
}