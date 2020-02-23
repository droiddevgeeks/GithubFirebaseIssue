package com.example.githubfirebaseissue.testutil

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class Rx2SchedulersOverrideRule : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement =
        object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { _ -> Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { _ -> Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }

                try {
                    base?.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
}