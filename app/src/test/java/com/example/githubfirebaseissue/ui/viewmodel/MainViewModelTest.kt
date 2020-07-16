package com.example.githubfirebaseissue.ui.viewmodel

import com.example.githubfirebaseissue.model.Issue
import com.example.githubfirebaseissue.testutil.Rx2SchedulersOverrideRule
import com.example.githubfirebaseissue.usecase.GetFireBaseIssueUseCase
import io.reactivex.Single
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.githubfirebaseissue.common.Event
import com.example.githubfirebaseissue.common.RxScheduler
import com.jraska.livedata.TestObserver
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @JvmField
    @Rule
    val rx2SchedulersOverrideRule = Rx2SchedulersOverrideRule()

    @JvmField
    @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var fireBaseUseCase: GetFireBaseIssueUseCase
    lateinit var scheduler: RxScheduler

    private lateinit var viewModel: MainViewModel
    private lateinit var loadingJraskaTestObserver: TestObserver<Boolean>
    private lateinit var loadingJraskaTestStates: List<Boolean>


    @Mock
    lateinit var dataObserver: Observer<Event<List<Issue>>>
    @Mock
    lateinit var loadingObserver: Observer<Boolean>
    @Mock
    lateinit var errorObserver: Observer<Event<Throwable>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = mock {
            on { io } doReturn Schedulers.trampoline()
            on { main } doReturn Schedulers.trampoline()
        }
        viewModel = MainViewModel(fireBaseUseCase, scheduler)
    }


    @Test
    fun `should return issues when fetching FireBase ios issue using Jraska Library way`() {
        //Assemble
        val issueList = listOf<Issue>()
        `when`(fireBaseUseCase.getFireBaseIosIssues()).thenReturn(Single.just(issueList))

        loadingJraskaTestObserver = TestObserver.test(viewModel.loadingState)
        loadingJraskaTestStates = loadingJraskaTestObserver.valueHistory()

        val dataObserver = TestObserver.test(viewModel.issueLiveData)

        //Act
        viewModel.fetchFireBaseIosIssueList()

        //Assert
        loadingJraskaTestObserver.assertHistorySize(2)
        assertEquals(true, loadingJraskaTestStates[0])
        assertEquals(false, loadingJraskaTestStates[1])
        assertEquals(dataObserver.value().getContentIfNotHandled(), issueList)
    }

    @Test
    fun `should return error when fetching FireBase ios issue using Jraska Library way`() {
        //Assemble
        val throwable = Throwable("Something went wrong")
        `when`(fireBaseUseCase.getFireBaseIosIssues()).thenReturn(Single.error(throwable))

        loadingJraskaTestObserver = TestObserver.test(viewModel.loadingState)
        loadingJraskaTestStates = loadingJraskaTestObserver.valueHistory()

        val errorObserver = TestObserver.test(viewModel.apiError)

        //Act
        viewModel.fetchFireBaseIosIssueList()

        //Assert
        loadingJraskaTestObserver.assertHistorySize(3)
        assertEquals(true, loadingJraskaTestStates[0])
        assertEquals(false, loadingJraskaTestStates[1])
        assertEquals(false, loadingJraskaTestStates[2])
        assertEquals(errorObserver.value().getContentIfNotHandled(), throwable)
    }

    @Test
    fun `should return issues when fetching FireBase ios issue using observer way`() {
        //Assemble
        val issueList = listOf<Issue>()
        `when`(fireBaseUseCase.getFireBaseIosIssues()).thenReturn(Single.just(issueList))

        viewModel.issueLiveData.observeForever(dataObserver)
        viewModel.loadingState.observeForever(loadingObserver)
        viewModel.apiError.observeForever(errorObserver)

        //Act
        viewModel.fetchFireBaseIosIssueList()

        //Assert
        verify(loadingObserver).onChanged(true)
        verify(loadingObserver).onChanged(false)
        assertEquals(viewModel.issueLiveData.value?.getContentIfNotHandled(), issueList)
        assertEquals(viewModel.apiError.value?.getContentIfNotHandled(), null)
    }

    @Test
    fun `should return error when fetching FireBase ios issue using observer way`() {
        //Assemble
        val throwable = Throwable("Something went wrong")
        `when`(fireBaseUseCase.getFireBaseIosIssues()).thenReturn(Single.error(throwable))

        viewModel.issueLiveData.observeForever(dataObserver)
        viewModel.loadingState.observeForever(loadingObserver)
        viewModel.apiError.observeForever(errorObserver)

        //Act
        viewModel.fetchFireBaseIosIssueList()

        //Assert
        verify(loadingObserver).onChanged(true)
        assertEquals(viewModel.issueLiveData.value?.getContentIfNotHandled(), null)
        assertEquals(viewModel.apiError.value?.getContentIfNotHandled(), throwable)
    }
}