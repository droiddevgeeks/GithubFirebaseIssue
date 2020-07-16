package com.example.githubfirebaseissue.ui.viewmodel

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.Observer
import com.example.githubfirebaseissue.common.Event
import com.example.githubfirebaseissue.common.RxScheduler
import com.example.githubfirebaseissue.model.Issue
import com.example.githubfirebaseissue.usecase.GetFireBaseIssueUseCase
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(JUnitPlatform::class)
class MainViewModelTestSpek : Spek({

    lateinit var fireBaseUseCase: GetFireBaseIssueUseCase
    lateinit var viewModel: MainViewModel
    lateinit var scheduler: RxScheduler
    lateinit var dataObserver: Observer<Event<List<Issue>>>
    lateinit var loadingObserver: Observer<Boolean>
    lateinit var errorObserver: Observer<Event<Throwable>>

    describe("MainViewModel Test Spek") {
        describe("When fetching FireBase ios issue From API") {
            beforeEachTest {
                fireBaseUseCase = mock()
                scheduler = mock {
                    on { io } doReturn Schedulers.trampoline()
                    on { main } doReturn Schedulers.trampoline()
                }
                viewModel = MainViewModel(fireBaseUseCase, scheduler)
                dataObserver = mock()
                loadingObserver = mock()
                errorObserver = mock()
                // In order to test LiveData, the `InstantTaskExecutorRule` rule needs to be applied via JUnit.
                // As we are running it with Spek, the "rule" will be implemented in this way instead
                ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
                    override fun executeOnDiskIO(runnable: Runnable) { runnable.run() }
                    override fun isMainThread(): Boolean { return true }
                    override fun postToMainThread(runnable: Runnable) { runnable.run() }
                })
            }
            afterEachTest { ArchTaskExecutor.getInstance().setDelegate(null) }
            context("When firebase ios issues fetch api successful") {
                it("Should return list of issues") {
                    val issueList = listOf(Mockito.mock(Issue::class.java))
                    `when`(fireBaseUseCase.getFireBaseIosIssues()).thenReturn(Single.just(issueList))

                    viewModel.issueLiveData.observeForever(dataObserver)
                    viewModel.loadingState.observeForever(loadingObserver)
                    viewModel.apiError.observeForever(errorObserver)

                    viewModel.fetchFireBaseIosIssueList()

                    verify(loadingObserver).onChanged(true)
                    verify(loadingObserver).onChanged(false)
                    assertEquals(
                        viewModel.issueLiveData.value?.getContentIfNotHandled(),
                        issueList
                    )
                    assertEquals(viewModel.apiError.value?.getContentIfNotHandled(), null)
                }
            }

            context("When firebase ios issues fetch api failed") {
                it("Should return error") {
                    val throwable = Throwable("Something went wrong")
                    `when`(fireBaseUseCase.getFireBaseIosIssues()).thenReturn(Single.error(throwable))

                    viewModel.issueLiveData.observeForever(dataObserver)
                    viewModel.loadingState.observeForever(loadingObserver)
                    viewModel.apiError.observeForever(errorObserver)

                    viewModel.fetchFireBaseIosIssueList()

                    verify(loadingObserver).onChanged(true)
                    assertEquals(viewModel.issueLiveData.value?.getContentIfNotHandled(), null)
                    assertEquals(viewModel.apiError.value?.getContentIfNotHandled(), throwable)
                }
            }
        }
    }
})