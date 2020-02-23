package com.example.githubfirebaseissue.usecase

import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.model.Issue
import com.example.githubfirebaseissue.repository.GithubRepositoryImpl
import com.example.githubfirebaseissue.testutil.Rx2SchedulersOverrideRule
import io.reactivex.Single
import junit.framework.Assert
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetFireBaseIssueUseCaseImplTest {


    @JvmField
    @Rule
    val rx2SchedulersOverrideRule = Rx2SchedulersOverrideRule()

    private lateinit var usecase: GetFireBaseIssueUseCase

    @Mock
    private lateinit var repo: GithubRepositoryImpl

    @Before
    fun setUp() {
        usecase = GetFireBaseIssueUseCaseImpl(repo)
    }


    @Test
    fun `should return FireBase ios issues successfully`() {

        val issueResponse = mock(Issue::class.java)
        val issueList = mutableListOf<Issue>()
        issueList.add(issueResponse)
        `when`(repo.getFireBaseIosIssues()).thenReturn(Single.just(issueList.toList()))

        usecase.getFireBaseIosIssues()
            .test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue {
                Assert.assertEquals(issueList, it)
                true
            }

        verify(repo).getFireBaseIosIssues()
        verifyNoMoreInteractions(repo)

    }


    @Test
    fun `should return FireBase ios comment successfully`() {

        val commentResponse = mock(Comment::class.java)
        val commentList = mutableListOf<Comment>()
        commentList.add(commentResponse)

        val number = 1234
        `when`(repo.getFireBaseIosUserComments(number)).thenReturn(Single.just(commentList.toList()))

        usecase.getFireBaseIssuesComments(number)
            .test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue {
                Assert.assertEquals(commentList, it)
                true
            }

        verify(repo).getFireBaseIosUserComments(number)
        verifyNoMoreInteractions(repo)

    }



}