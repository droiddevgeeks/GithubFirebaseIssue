package com.example.githubfirebaseissue.common

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RxScheduler {
    val io: Scheduler
    val main: Scheduler
}

class BaseSchedulerProvider: RxScheduler {
    override val io: Scheduler
        get() = Schedulers.io()
    override val main: Scheduler
        get() = AndroidSchedulers.mainThread()
}
