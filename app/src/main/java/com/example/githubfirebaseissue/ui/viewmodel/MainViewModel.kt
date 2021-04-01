package com.example.githubfirebaseissue.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubfirebaseissue.common.Event
import com.example.githubfirebaseissue.common.RxScheduler
import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.model.Issue
import com.example.githubfirebaseissue.usecase.GetFireBaseIssueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetFireBaseIssueUseCase,
    private val scheduler: RxScheduler
) : ViewModel() {

    private val _issueLiveData by lazy { MutableLiveData<Event<List<Issue>>>() }
    val issueLiveData: LiveData<Event<List<Issue>>> by lazy { _issueLiveData }

    private val _commentsLiveData by lazy { MutableLiveData<Event<List<Comment>>>() }
    val commentsLiveData: LiveData<Event<List<Comment>>> by lazy { _commentsLiveData }

    var loadingState = MutableLiveData<Boolean>()

    private val _apiError by lazy { MutableLiveData<Event<Throwable>>() }
    val apiError: LiveData<Event<Throwable>> by lazy { _apiError }

    private val disposable by lazy { CompositeDisposable() }


    fun fetchFireBaseIosIssueList() {
        val issueDisposable = useCase.getFireBaseIosIssues()
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.main)
            .doOnSubscribe { loadingState.value = true }
            .doOnEvent { _, _ -> loadingState.value = false }
            .doOnError { loadingState.value = false }
            .map { it.sortedByDescending { issue -> issue.updatedAt } }
            .subscribe(
                { Event(it).run(_issueLiveData::postValue) },
                { Event(it).run(_apiError::postValue) }
            )
        disposable.add(issueDisposable)
    }

    fun fetchIssueComments(number: Int) {
        val commentDisposable = useCase.getFireBaseIssuesComments(number)
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.main)
            .doOnSubscribe { loadingState.value = true }
            .doOnEvent { _, _ -> loadingState.value = false }
            .doOnError { loadingState.value = false }
            .subscribe(
                { Event(it).run(_commentsLiveData::postValue) },
                { Event(it).run(_apiError::postValue) }
            )
        disposable.add(commentDisposable)
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}