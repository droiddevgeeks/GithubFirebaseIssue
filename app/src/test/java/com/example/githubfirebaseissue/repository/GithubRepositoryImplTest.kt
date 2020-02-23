package com.example.githubfirebaseissue.repository

import com.example.githubfirebaseissue.api.GithubApi
import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.model.Issue
import com.example.githubfirebaseissue.testutil.Rx2SchedulersOverrideRule
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody


@RunWith(MockitoJUnitRunner::class)
class GithubRepositoryImplTest {

    @JvmField
    @Rule
    val rx2SchedulersOverrideRule = Rx2SchedulersOverrideRule()

    private lateinit var repo: GithubRepository

    @Mock
    private lateinit var endpoint: GithubApi

    @Before
    fun setUp() {
        repo = GithubRepositoryImpl(endpoint)
    }

    @Test
    fun `should return issues when fetching FireBase ios issue`() {
        val expectedResponse = listOf(mock(Issue::class.java))
        `when`(endpoint.getFireBaseIosIssues()).thenReturn(Single.create {
            it.onSuccess(
                expectedResponse
            )
        })
        endpoint.getFireBaseIosIssues()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue(expectedResponse)
    }


    @Test
    fun `should return error when fetching FireBase ios issue`() {

        val body = Response.error<Issue>(
            400,
            "{\"key\":[\"something went wrong\"]}".toResponseBody("application/json".toMediaTypeOrNull())
        )
        val exception = HttpException(body)

        `when`(endpoint.getFireBaseIosIssues()).thenReturn(Single.error(exception))
        repo.getFireBaseIosIssues()
            .test()
            .assertValueCount(0)
            .assertError { it is HttpException }

        verify(endpoint).getFireBaseIosIssues()

    }


    @Test
    fun `should return comments when fetching FireBase ios issue comments`() {
        val expectedResponse = listOf(mock(Comment::class.java))
        `when`(endpoint.getFireBaseIosUserComments(1234)).thenReturn(Single.create {
            it.onSuccess(
                expectedResponse
            )
        })
        endpoint.getFireBaseIosUserComments(1234)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue(expectedResponse)
    }


    @Test
    fun `should return error when fetching FireBase ios issue comments`() {

        val body = Response.error<Comment>(
            400,
            "{\"key\":[\"something went wrong\"]}".toResponseBody("application/json".toMediaTypeOrNull())
        )
        val exception = HttpException(body)

        `when`(endpoint.getFireBaseIosUserComments(1234)).thenReturn(Single.error(exception))
        repo.getFireBaseIosUserComments(1234)
            .test()
            .assertValueCount(0)
            .assertError { it is HttpException }

        verify(endpoint).getFireBaseIosUserComments(1234)

    }

}